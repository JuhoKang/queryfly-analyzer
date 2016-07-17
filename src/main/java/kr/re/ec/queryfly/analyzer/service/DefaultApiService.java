package kr.re.ec.queryfly.analyzer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.re.ec.queryfly.analyzer.core.ApiService;


@Service("notFound")
public class DefaultApiService implements ApiService {

  @Override
  public String serve(Map<String, String> request) throws ServiceException {
    JsonObject response = new JsonObject();
    response.addProperty("resultCode", "404");
    return response.toString();
  }
}
