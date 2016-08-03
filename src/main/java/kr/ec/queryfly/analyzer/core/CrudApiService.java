package kr.ec.queryfly.analyzer.core;

import java.util.Map;

import kr.ec.queryfly.analyzer.web.service.RequestParamException;

public interface CrudApiService extends ApiService{
  
  public String whenGet(Map<String, String> request)
      throws RequestParamException;

  public String whenPost(Map<String, String> request)
      throws RequestParamException;

  public String whenPut(Map<String, String> request)
      throws RequestParamException;

  public String whenDelete(Map<String, String> request)
      throws RequestParamException;


}
