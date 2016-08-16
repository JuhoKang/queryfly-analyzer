package kr.ec.queryfly.analyzer.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.Assert;

public class FlybaseRepositoryImpl implements CustomFlybaseRepository {


  private final MongoOperations operations;

  @Autowired
  public FlybaseRepositoryImpl(MongoOperations operations) {
    Assert.notNull(operations);
    this.operations = operations;
  }
}
