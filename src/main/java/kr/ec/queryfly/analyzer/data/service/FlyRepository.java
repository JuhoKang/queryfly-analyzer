package kr.ec.queryfly.analyzer.data.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import kr.ec.queryfly.analyzer.model.Fly;

public interface FlyRepository extends MongoRepository<Fly, ObjectId>, CustomFlyRepository {

  Page<Fly> findByFlybaseId(ObjectId flybaseId, Pageable pageable);

}
