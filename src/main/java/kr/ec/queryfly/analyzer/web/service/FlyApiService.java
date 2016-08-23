package kr.ec.queryfly.analyzer.web.service;

import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.PREFIX_POST;
import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.REQUEST_URI;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.util.GsonUtil;

@Service("flyApiService")
public class FlyApiService extends SimpleCrudApiService {


  private final FlyRepository flyRepo;

  private final GsonUtil gson;

  @Autowired
  public FlyApiService(FlyRepository flyRepo, GsonUtil gson) {
    this.flyRepo = flyRepo;
    this.gson = gson;
  }

  @Override
  public String whenGet(ApiRequest request) throws RequestParamException {

    List<String> uriElements = getSplittedPath(request.getUri());

    if (uriElements.size() == 2) {
      String flyId = uriElements.get(1);
      Fly resultFly = flyRepo.findOne(new ObjectId(flyId));
      return gson.toJson(resultFly);
    } else if (uriElements.size() == 3) {
      String flyId = uriElements.get(1);
      String operation = uriElements.get(2);
      if (operation.equals("stat")) {
        // TODO
        // do the stat thing.
        return null;
      } else {
        throw new RequestParamException();
      }
    } else {
      throw new RequestParamException();
    }
  }

  @Override
  public String whenPost(ApiRequest request) throws RequestParamException {
    if (!request.getPostFormData().containsKey("flybase_key")) {
      throw new RequestParamException("flybase_key is null");
    } else if (!request.getPostFormData().containsKey("content")) {
      throw new RequestParamException("content is null");
    }

    String contentJson = request.getPostFormData().get("content");
    ObjectId flybaseId = new ObjectId(request.getPostFormData().get("flybase_key"));

    Fly contentFly = gson.getGson().fromJson(contentJson, Fly.class);
    System.out.println(contentFly);
    Fly fly = new Fly.Builder(contentFly.getQaPairs()).flybaseId(flybaseId).build();

    Fly resultFly = flyRepo.save(fly);

    return gson.toJson(resultFly);
  }

  @Override
  public String whenPut(ApiRequest request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String whenDelete(ApiRequest request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

}
