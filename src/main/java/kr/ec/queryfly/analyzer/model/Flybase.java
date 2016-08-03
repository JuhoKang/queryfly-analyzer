package kr.ec.queryfly.analyzer.model;

import java.time.ZonedDateTime;
import java.util.List;

import org.bson.types.ObjectId;

public class Flybase {

  private ObjectId id;
  private List<Fly> flies;
  private ZonedDateTime createTime;

}
