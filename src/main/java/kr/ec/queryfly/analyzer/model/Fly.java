package kr.ec.queryfly.analyzer.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Fly {

  @Id
  private final String id;
  @Indexed(name = "flybaseId")
  private final String flybaseId;
  private final List<QaPair> qaPairs;

  public String getId() {
    return id;
  }

  public String getFlybaseId() {
    return flybaseId;
  }

  public List<QaPair> getQaPairs() {
    return qaPairs;
  }

  private Fly(Builder builder) {
    this.id = builder.id;
    this.flybaseId = builder.flybaseId;
    this.qaPairs = builder.qaPairs;
  }

  public static class Builder {


    private String id;
    private final String flybaseId;
    private final List<QaPair> qaPairs;

    public Builder(String flybaseId, List<QaPair> qaPairs) {
      if (flybaseId == null || qaPairs == null) {
        throw new IllegalArgumentException("flybaseId or qaPairs can't be null");
      }
      this.flybaseId = flybaseId;
      this.qaPairs = qaPairs;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Fly build() {
      return new Fly(this);
    }

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((flybaseId == null) ? 0 : flybaseId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((qaPairs == null) ? 0 : qaPairs.hashCode());
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
    Fly other = (Fly) obj;
    if (flybaseId == null) {
      if (other.flybaseId != null)
        return false;
    } else if (!flybaseId.equals(other.flybaseId))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (qaPairs == null) {
      if (other.qaPairs != null)
        return false;
    } else if (!qaPairs.equals(other.qaPairs))
      return false;
    return true;
  }


}
