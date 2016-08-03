package kr.ec.queryfly.analyzer.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class FlyRepositoryImpl implements CustomFlyRepository {
  private final MongoOperations operations;

  @Autowired
  public FlyRepositoryImpl(MongoOperations operations) {
    Assert.notNull(operations);
    this.operations = operations;
  }


}
