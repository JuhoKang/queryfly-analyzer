package kr.ec.queryfly.analyzer.core;

import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;

public interface CrudApiService extends ApiService {

  public String whenGet(ApiRequest request) throws RequestParamException;

  public String whenPost(ApiRequest request) throws RequestParamException;

  public String whenPut(ApiRequest request) throws RequestParamException;

  public String whenDelete(ApiRequest request) throws RequestParamException;

}
