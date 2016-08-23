package kr.ec.queryfly.analyzer.model;

import java.time.ZonedDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Flybase {

  @Id
  private final ObjectId id;
  private final String name;
  private final String description;
  private final ZonedDateTime createTime;
  private final List<String> keywords;

  private Flybase(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.description = builder.description;
    this.createTime = builder.createTime;
    this.keywords = builder.keywords;
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

  public List<String> getKeywords() {
    return keywords;
  }

  public static class Builder {

    private ObjectId id;
    private final String name;
    private String description;
    private ZonedDateTime createTime;
    private List<String> keywords;

    public Builder(String name) {
      if (name == null) {
        throw new IllegalArgumentException("name can't be null");
      }
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
    
    public Builder keywords(List<String> keywords){
      this.keywords = keywords;
      return this;
    }

    public Flybase build() {
      return new Flybase(this);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Flybase other = (Flybase) obj;
    if (createTime == null) {
      if (other.createTime != null)
        return false;
    } else if (!createTime.equals(other.createTime))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("Flybase [id=");
    builder2.append(id);
    builder2.append(", name=");
    builder2.append(name);
    builder2.append(", description=");
    builder2.append(description);
    builder2.append(", createTime=");
    builder2.append(createTime);
    builder2.append("]");
    return builder2.toString();
  }

}
