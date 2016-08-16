package kr.ec.queryfly.analyzer.data.service;

import java.util.List;

import kr.ec.queryfly.analyzer.model.Fly;

public interface CustomFlyRepository {
  
  public List<Fly> findFliesByFlybaseId(String id);
  
}
