package kr.ec.queryfly.analyzer.data.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import kr.ec.queryfly.analyzer.model.Flybase;

public interface FlybaseRepository
    extends MongoRepository<Flybase, ObjectId>, CustomFlybaseRepository {
  
}
