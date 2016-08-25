package kr.ec.queryfly.analyzer.web.service;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.model.ApiRequest;


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
    JsonObject response = new JsonObject();
    response.addProperty("resultCode", "404");
    return response.toString();
  }

}
