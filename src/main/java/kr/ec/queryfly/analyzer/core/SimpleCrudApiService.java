package kr.ec.queryfly.analyzer.core;

import java.util.List;

import com.google.common.base.Splitter;

import io.netty.handler.codec.http.QueryStringDecoder;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;
import kr.ec.queryfly.analyzer.web.service.ServiceException;
/**
 * Abstract Class for writing a simple {@link ApiService}.<br>
 * Extend this class and implement get,post,delete,update features<br>
 * If you don't want to use one of those methods throw a {@link RequestParamException}
 * 
 * @author Juho Kang
 *
 */
public abstract class SimpleCrudApiService implements CrudApiService {

  @Override
  public String serve(ApiRequest request) throws ServiceException, RequestParamException {

    String httpMethod = request.getMethod();
    String result = "";
    switch (httpMethod) {
      case "POST":
        result = whenPost(request);
        break;
      case "DELETE":
        result = whenDelete(request);
        break;
      case "GET":
        result = whenGet(request);
        break;
      case "UPDATE":
        result = whenPut(request);
        break;
      default:
        throw new RequestParamException();
    }

    if (result.equals("")) {
      throw new UnknownError();
    }
    return result;
  }

  /**
   * util method to split the requested uri.<br>
   * ex) /fly/stat?name=abc --> 0 : fly 1 : stat<br>
   * this method works only for uris which start with a "/"
   * 
   * @param requestUri
   * @return
   */
  protected List<String> getSplittedPath(String requestUri) {
    // get rid of the first "/"
    String uri = new QueryStringDecoder(requestUri).path().substring(1);
    List<String> result = Splitter.onPattern("/").splitToList(uri);

    // uriElements[0] is fly.
    return result;
  }

}
