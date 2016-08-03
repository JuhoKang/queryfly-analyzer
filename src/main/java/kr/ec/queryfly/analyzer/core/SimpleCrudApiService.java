package kr.ec.queryfly.analyzer.core;

import java.util.Map;

import kr.ec.queryfly.analyzer.web.service.RequestParamException;
import kr.ec.queryfly.analyzer.web.service.ServiceException;

/**
 * Abstract Class for writing a simple {@link ApiService}.<br>
 * Extend this class and implement get,post,delete,update features<br>
 * If you don't want to use one of those methods throw a {@link RequestParamException}
 * 
 * @author MNDCERT
 *
 */
public abstract class SimpleCrudApiService implements CrudApiService {

  @Override
  public String serve(Map<String, String> request)
      throws ServiceException, RequestParamException {

    String httpMethod = request.get("REQUEST_METHOD");
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

}
