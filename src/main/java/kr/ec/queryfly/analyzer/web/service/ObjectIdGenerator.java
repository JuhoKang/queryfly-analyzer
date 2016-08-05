package kr.ec.queryfly.analyzer.web.service;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;

/**
 * Generates an ObjectId for a Fly<br>
 * 
 * @author JuhoKang
 *
 */
@Service("idGenerator")
public class ObjectIdGenerator extends SimpleCrudApiService {

  @Override
  public String whenGet(Map<String, String> request) {
    JsonObject result = new JsonObject();
    result.addProperty("id", ObjectId.get().toHexString());
    return result.toString();
  }

  @Override
  public String whenPost(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenPut(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenDelete(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

}