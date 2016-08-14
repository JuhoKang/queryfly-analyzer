package kr.ec.queryfly.analyzer.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.util.GsonUtil;

public class FlyService extends SimpleCrudApiService {

  @Autowired
  FlyRepository flyRepo;

  @Autowired
  GsonUtil gson;

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String whenPost(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
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
