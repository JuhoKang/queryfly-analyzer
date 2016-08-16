package kr.ec.queryfly.analyzer.web.service;

import java.util.List;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ec.queryfly.analyzer.core.ApiRequestHandler;
import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.util.GsonUtil;

@Service("flybaseApiService")
public class FlybaseApiService extends SimpleCrudApiService {

  @Autowired
  FlybaseRepository flybaseRepo;

  @Autowired
  FlyRepository flyRepo;

  @Autowired
  GsonUtil gson;

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    List<String> uriElements = splitRequestUri(request.get(ApiRequestHandler.REQUEST_URI));
    if (uriElements.size() > 1) {
      throw new RequestParamException();
    }

    List<Flybase> flybaseList = flybaseRepo.findAll();

    return gson.toJson(flybaseList);
  }

  @Override
  public String whenPost(Map<String, String> request) throws RequestParamException {

    if (!request.containsKey("name") || !request.containsKey("description")) {
      throw new RequestParamException();
    }

    Flybase flybase = new Flybase.Builder(request.get("name"))
        .description(request.get("description")).createTime(ZonedDateTime.now()).build();
    Flybase resultFlybase = flybaseRepo.save(flybase);
    return gson.toJson(resultFlybase);
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
