package kr.ec.queryfly.analyzer.data.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.Assert;

import kr.ec.queryfly.analyzer.model.Flybase;

public class FlybaseRepositoryImpl implements CustomFlybaseRepository {


  private final MongoOperations operations;

  @Autowired
  public FlybaseRepositoryImpl(MongoOperations operations) {
    Assert.notNull(operations);
    this.operations = operations;
  }
  
}
