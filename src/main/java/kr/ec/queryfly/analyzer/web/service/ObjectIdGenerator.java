package kr.ec.queryfly.analyzer.web.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.model.ApiRequest;

/**
 * Generates an ObjectId for a Fly<br>
 * 
 * @author JuhoKang
 *
 */
@Service("idGenerator")
public class ObjectIdGenerator extends SimpleCrudApiService {

  @Override
  public String whenGet(ApiRequest request) {
    JsonObject result = new JsonObject();
    result.addProperty("id", ObjectId.get().toHexString());
    return result.toString();
  }

  @Override
  public String whenPost(ApiRequest request) throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenPut(ApiRequest request) throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenDelete(ApiRequest request) throws RequestParamException {
    throw new RequestParamException();
  }

}
