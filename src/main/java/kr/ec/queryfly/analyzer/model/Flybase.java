package kr.ec.queryfly.analyzer.model;

import java.time.ZonedDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Flybase {

  @Id
  private final ObjectId id;
  private final String name;
  private final String description;
  private final ZonedDateTime createTime;

  private Flybase(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.description = builder.description;
    this.createTime = builder.createTime;
  }

  public ObjectId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ZonedDateTime getCreateTime() {
    return createTime;
  }

  public static class Builder {

    private ObjectId id;
    private final String name;
    private String description;
    private ZonedDateTime createTime;

    public Builder(String name) {
      this.name = name;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder id(ObjectId id) {
      this.id = id;
      return this;
    }

    public Builder createTime(ZonedDateTime createTime) {
      this.createTime = createTime;
      return this;
    }

    public Flybase build() {
      return new Flybase(this);
    }



  }

}
