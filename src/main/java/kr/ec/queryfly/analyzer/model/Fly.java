package kr.ec.queryfly.analyzer.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Fly {

  @Id
  private final ObjectId id;
  private final List<QaPair> qaPairs;

  public ObjectId getId() {
    return id;
  }

  public List<QaPair> getQaPairs() {
    return qaPairs;
  }

  private Fly(Builder builder) {
    this.id = builder.id;
    this.qaPairs = builder.qaPairs;
  }

  public static class Builder {


    private ObjectId id;
    private final List<QaPair> qaPairs;

    public Builder(List<QaPair> qaPairs) {
      this.qaPairs = qaPairs;
    }

    public Builder id(ObjectId id) {
      this.id = id;
      return this;
    }

    public Fly build() {
      return new Fly(this);
    }

  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("Fly [id=");
    builder2.append(id);
    builder2.append(", qaPairs=");
    builder2.append(qaPairs);
    builder2.append("]");
    return builder2.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
