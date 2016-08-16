package kr.ec.queryfly.analyzer.data.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import kr.ec.queryfly.analyzer.model.Fly;

@Repository
public class FlyRepositoryImpl implements CustomFlyRepository {
  
  private final MongoOperations operations;

  @Autowired
  public FlyRepositoryImpl(MongoOperations operations) {
    Assert.notNull(operations);
    this.operations = operations;
  }
  
  public List<Fly> findFliesByFlybaseId(String id){
    return operations.find(new Query(Criteria.where("flybaseId").is(new ObjectId(id))), Fly.class);
  }


}
