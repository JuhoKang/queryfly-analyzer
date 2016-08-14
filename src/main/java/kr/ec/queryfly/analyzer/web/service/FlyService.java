package kr.ec.queryfly.analyzer.web.service;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ec.queryfly.analyzer.core.ApiRequestHandler;
import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.util.GsonUtil;

@Service("flyService")
public class FlyService extends SimpleCrudApiService {

  @Autowired
  FlyRepository flyRepo;

  @Autowired
  GsonUtil gson;

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    String uri = request.get(ApiRequestHandler.REQUEST_URI);
    String[] uriElements = uri.split("/");
    // uriElements[1] is fly.
    for(String e : uriElements){
      System.out.println("element : "+e);
    }

    if (uriElements.length == 3) {
      String flyId = uriElements[2];
      Fly resultFly = flyRepo.findOne(new ObjectId(flyId));
      return gson.toJson(resultFly);
    } else if (uriElements.length == 4) {
      String flyId = uriElements[2];
      String operation = uriElements[3];
      if (operation.equals("stat")) {
        // do the stat thing.
        return null;
      }
    } else {
      throw new RequestParamException();
    }
    return null;
  }

  @Override
  public String whenPost(Map<String, String> request) throws RequestParamException {
    if (!request.containsKey("flybase_key")) {
      throw new RequestParamException("flybase_key is null");
    } else if (!request.containsKey("content")) {
      throw new RequestParamException("content is null");
    }

    String contentJson = request.get("content");
    ObjectId flybaseId = new ObjectId(request.get("flybase_key"));

    Fly contentFly = gson.getGson().fromJson(contentJson, Fly.class);
    System.out.println(contentFly);
    Fly fly = new Fly.Builder(flybaseId, contentFly.getQaPairs()).build();

    Fly resultFly = flyRepo.save(fly);

    return gson.toJson(resultFly);
  }

  @Override
  public String whenPut(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String whenDelete(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

}
