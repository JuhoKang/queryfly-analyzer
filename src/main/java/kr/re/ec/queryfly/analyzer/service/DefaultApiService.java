package kr.re.ec.queryfly.analyzer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.re.ec.queryfly.analyzer.core.SimpleCrudApiService;


@Service("notFound")
public class DefaultApiService extends SimpleCrudApiService {

  @Override
  public String whenGet(Map<String, String> request)
      throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenPost(Map<String, String> request)
      throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenPut(Map<String, String> request)
      throws RequestParamException {
    return jsonNotFound();
  }

  @Override
  public String whenDelete(Map<String, String> request)
      throws RequestParamException {
    return jsonNotFound();
  }
  
  private String jsonNotFound(){
    JsonObject response = new JsonObject();
    response.addProperty("resultCode", "404");
    return response.toString();
  }

}
