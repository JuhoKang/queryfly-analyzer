package kr.ec.queryfly.analyzer.data.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import kr.ec.queryfly.analyzer.model.Fly;

public interface FlyRepository extends MongoRepository<Fly, ObjectId>, CustomFlyRepository{

}
