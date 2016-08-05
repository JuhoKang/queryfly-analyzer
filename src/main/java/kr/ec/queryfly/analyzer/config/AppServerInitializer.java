package kr.ec.queryfly.analyzer.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import kr.ec.queryfly.analyzer.core.ApiRequestHandler;

public class AppServerInitializer extends ChannelInitializer<SocketChannel> {

  private final SslContext sslCtx;

  public AppServerInitializer(SslContext sslCtx) {
    this.sslCtx = sslCtx;
  }

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline p = ch.pipeline();
    if (sslCtx != null) {
      p.addLast(sslCtx.newHandler(ch.alloc()));
    }

    p.addLast(new HttpRequestDecoder());
    p.addLast(new HttpObjectAggregator(65536));
    p.addLast(new HttpResponseEncoder());
    p.addLast(new HttpContentCompressor());
    p.addLast(new ApiRequestHandler());

  }

}