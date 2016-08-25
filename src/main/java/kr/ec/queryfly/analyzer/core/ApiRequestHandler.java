package kr.ec.queryfly.analyzer.core;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.model.CustomMediaType;
import kr.ec.queryfly.analyzer.util.JsonResult;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;
import kr.ec.queryfly.analyzer.web.service.ServiceException;

public class ApiRequestHandler extends SimpleChannelInboundHandler<FullHttpMessage> {

  private static final Logger logger = LoggerFactory.getLogger(ApiRequestHandler.class);

  private ApiRequest apiReq;

  private HttpRequest request;

  private static final HttpDataFactory factory =
      new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk


  /*
   * private static final Set<String> usingHeader = new HashSet<String>(); static {
   * usingHeader.add("Content-Type"); }
   */

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    logger.info("요청 처리 완료");
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) {
    // Request header 처리.

    this.request = (HttpRequest) msg;

    if (HttpUtil.is100ContinueExpected(msg)) {
      send100Continue(ctx);
    }

    QueryStringDecoder decoder = new QueryStringDecoder(request.uri());

    apiReq = new ApiRequest.Builder(decoder.path(), request.method().name()).build();

    // headers
    HttpHeaders headers = request.headers();
    Map<String, String> headerMap = new HashMap<String, String>();
    for (Map.Entry<String, String> h : headers) {
      String key = h.getKey();
      // if (usingHeader.contains(key)) {
      headerMap.put(key, h.getValue());
      // }
    }
    apiReq = ApiRequest.Builder.from(apiReq).headers(headerMap).build();

    // QueryString Parameter 처리
    if (apiReq.getMethod().equals("GET")) {
      apiReq = ApiRequest.Builder.from(apiReq).parameters(decoder.parameters()).build();
    }

    logger.info("message content" + msg.content().toString(StandardCharsets.UTF_8));

    // Request content 처리.
    if (apiReq.getHeaders().containsKey("Content-Type")) {

      if (apiReq.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)
          .contains(CustomMediaType.APPLICATION_JSON_VALUE)) {

        logger.info("request content-type : application/json");
        apiReq = ApiRequest.Builder.from(apiReq)
            .postRawData(msg.content().toString(StandardCharsets.UTF_8)).build();

      } else if (apiReq.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)
          .contains(CustomMediaType.MULTIPART_FORM_DATA_VALUE)) {

        readPostFormData();

      } else if (apiReq.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)
          .contains("application/csv")) {

        apiReq = ApiRequest.Builder.from(apiReq)
            .postRawData(msg.content().toString(StandardCharsets.UTF_8)).build();

      } else {

        logger.info("request content-type : not known type");
        logger.info("request content-type : "
            + apiReq.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE));

      }

    } else {
      logger.error("request doesn't have a content-type");
    }

    ApiService service = ServiceDispatcher.dispatch(apiReq);
    String apiResult = "";

    logger.debug(apiReq.toString());

    try {
      apiResult = service.serve(apiReq);
    } catch (ServiceException e) {
      apiResult = new JsonResult().httpResult(501, e.getMessage());
    } catch (RequestParamException e) {
      apiResult = new JsonResult().httpResult(400, e.getMessage());
    } catch (Exception e) {
      apiResult = new JsonResult().httpResult(500, e.getMessage());
      e.printStackTrace();
    } finally {
      apiReq = null;
    }

    if (!writeResponse(msg, ctx, apiResult)) {
      // If keep-alive is off, close the connection once the
      // content is fully written.
      ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
    reset();
  }


  private void reset() {
    request = null;
  }

  private void readPostFormData() {
    HttpPostRequestDecoder decoder = null;
    Map<String, String> postData = new HashMap<String, String>();
    try {
      decoder = new HttpPostRequestDecoder(factory, request);
      decoder.getBodyHttpDatas().stream()
          .filter(data -> data.getHttpDataType() == HttpDataType.Attribute)
          .map(Attribute.class::cast).forEach(attribute -> {
            try {
              postData.put(attribute.getName(), attribute.getValue());
            } catch (IOException e) {
              logger.error("BODY Attribute: " + attribute.getHttpDataType().name(), e);
              e.printStackTrace();
            }
          });
      /*
       * for (InterfaceHttpData data : decoder.getBodyHttpDatas()) { if (data.getHttpDataType() ==
       * HttpDataType.Attribute) { try { Attribute attribute = (Attribute) data;
       * reqData.put(attribute.getName(), attribute.getValue()); } catch (IOException e) {
       * logger.error("BODY Attribute: " + data.getHttpDataType().name(), e); return; } } else {
       * logger.info( "BODY data : " + data.getHttpDataType().name() + ": " + data); } }
       */
    } catch (ErrorDataDecoderException e) {
      logger.error(e.getMessage());
    } finally {
      if (decoder != null) {
        decoder.destroy();
      }
    }
    apiReq = ApiRequest.Builder.from(apiReq).postFormData(postData).build();
  }

  private boolean writeResponse(HttpMessage msg, ChannelHandlerContext ctx, String apiResult) {
    boolean keepAlive = HttpUtil.isKeepAlive(msg);

    FullHttpResponse response =
        new DefaultFullHttpResponse(HTTP_1_1, msg.decoderResult().isSuccess() ? OK : BAD_REQUEST,
            Unpooled.copiedBuffer(apiResult.toString(), CharsetUtil.UTF_8));

    response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");

    if (keepAlive) {

      // Add 'Content-Length' header only for a keep-alive connection.
      response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
      // Add keep alive header as per
      // http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
      response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);

    }

    ctx.write(response);
    return keepAlive;
  }

  private static void send100Continue(ChannelHandlerContext ctx) {
    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
    ctx.write(response);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.info("" + cause.getClass());
    logger.error("cause : " + cause.getMessage());
    cause.printStackTrace();
    ctx.close();
  }

}
