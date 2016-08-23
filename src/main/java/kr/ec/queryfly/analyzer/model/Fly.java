package kr.ec.queryfly.analyzer.model;

import java.time.ZonedDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * User Builder to build this object.
 * 
 * @author Juho Kang
 *
 */
@Document
public class Fly {

  @Id
  private final ObjectId id;
  @Indexed(name = "flybaseId")
  private final ObjectId flybaseId;
  private final List<QaPair> qaPairs;
  private final ZonedDateTime createTime;

  public ObjectId getId() {
    return id;
  }

  public ObjectId getFlybaseId() {
    return flybaseId;
  }

  public List<QaPair> getQaPairs() {
    return qaPairs;
  }


  public ZonedDateTime getCreateTime() {
    return createTime;
  }

  private Fly(Builder builder) {
    this.id = builder.id;
    this.flybaseId = builder.flybaseId;
    this.qaPairs = builder.qaPairs;
    this.createTime = builder.createTime;
  }

  /**
   * mandatory : String flybaseId, List<QaPair> qaPairs
   * 
   * @author mndcert
   *
   */
  public static class Builder {

    private ObjectId id;
    private ObjectId flybaseId;
    private final List<QaPair> qaPairs;
    private ZonedDateTime createTime;

    public Builder(List<QaPair> qaPairs) {
      if (qaPairs == null) {
        throw new IllegalArgumentException("qaPairs can't be null");
      }
      this.qaPairs = qaPairs;
    }

    public Builder id(ObjectId id) {
      this.id = id;
      return this;
    }

    public Builder flybaseId(ObjectId flybaseId) {
      this.flybaseId = flybaseId;
      return this;
    }

    public Builder createTime(ZonedDateTime createTime) {
      this.createTime = createTime;
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
    builder2.append(", flybaseId=");
    builder2.append(flybaseId);
    builder2.append(", qaPairs=");
    builder2.append(qaPairs);
    builder2.append(", createTime=");
    builder2.append(createTime);
    builder2.append("]");
    return builder2.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
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
    if (createTime == null) {
      if (other.createTime != null)
        return false;
    } else if (!createTime.equals(other.createTime))
      return false;
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
