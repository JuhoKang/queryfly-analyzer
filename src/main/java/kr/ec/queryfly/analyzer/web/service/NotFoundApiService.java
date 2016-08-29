package kr.ec.queryfly.analyzer.web.service;

import org.springframework.stereotype.Service;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.util.JsonResult;


@Service("notFound")
public class NotFoundApiService extends SimpleCrudApiService {

  @Override
  public String whenGet(ApiRequest request) throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenPost(ApiRequest request) throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenPut(ApiRequest request) throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenDelete(ApiRequest request) throws RequestParamException {
    return jsonNotFound();
  }

  private String jsonNotFound() {
    return new JsonResult().httpResult(404, "notFound");
  }

}
