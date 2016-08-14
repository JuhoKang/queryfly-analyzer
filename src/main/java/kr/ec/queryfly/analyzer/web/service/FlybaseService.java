package kr.ec.queryfly.analyzer.web.service;

import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.util.GsonUtil;

@Service("flybaseService")
public class FlybaseService extends SimpleCrudApiService {

  @Autowired
  FlybaseRepository flybaseRepo;

  @Autowired
  GsonUtil gson;

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
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
