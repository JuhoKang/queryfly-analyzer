package kr.re.ec.queryfly.analyzer.core;

import java.util.Map;

import com.google.gson.JsonObject;

import kr.re.ec.queryfly.analyzer.service.RequestParamException;

/**
 * An abstract implemantation of ApiService for Json results
 * 
 * @author MNDCERT
 *
 */
@Deprecated
public abstract class JsonApiService implements ApiService {

  /**
   * API 요청 data
   */
  protected Map<String, String> reqData;

  /**
   * API 처리결과
   */
  protected JsonObject jsonResult;

  /**
   * logger 생성<br/>
   * apiResult 객체 생성
   */
  public JsonApiService(Map<String, String> reqData) {
    this.jsonResult = new JsonObject();
    this.reqData = reqData;
  }

  public void executeService() {
    try {
      this.requestParamValidation();

    //  this.service();
    } catch (RequestParamException e) {
      this.jsonResult.addProperty("resultCode", "405");
    } 
    //catch (ServiceException e) {
    //  this.jsonResult.addProperty("resultCode", "501");
    //}
  }

  public JsonObject getApiResult() {
    return this.jsonResult;
  }

  public void requestParamValidation() throws RequestParamException {
    if (getClass().getClasses().length == 0) {
      return;
    }

    // // TODO 이건 꼼수 바꿔야 하는데..
    // for (Object item :
    // this.getClass().getClasses()[0].getEnumConstants()) {
    // RequestParam param = (RequestParam) item;
    // if (param.isMandatory() && this.reqData.get(param.toString()) ==
    // null) {
    // throw new RequestParamException(item.toString() +
    // " is not present in request param.");
    // }
    // }
  }

  public final <T extends Enum<T>> T fromValue(Class<T> paramClass,
      String paramValue) {
    if (paramValue == null || paramClass == null) {
      throw new IllegalArgumentException("There is no value with name '"
          + paramValue + " in Enum " + paramClass.getClass().getName());
    }

    for (T param : paramClass.getEnumConstants()) {
      if (paramValue.equals(param.toString())) {
        return param;
      }
    }

    throw new IllegalArgumentException("There is no value with name '"
        + paramValue + " in Enum " + paramClass.getClass().getName());
  }

}
