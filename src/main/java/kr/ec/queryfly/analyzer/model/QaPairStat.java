package kr.ec.queryfly.analyzer.model;

import java.util.List;

public class QaPairStat {

  private final String question;
  private final List<AcPair> acPairs;

  public String getQuestion() {
    return question;
  }

  public List<AcPair> getAcPairs() {
    return acPairs;
  }

  public QaPairStat(String question, List<AcPair> acPairs) {
    super();
    this.question = question;
    this.acPairs = acPairs;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("QaPairStat [question=");
    builder.append(question);
    builder.append(", acPairs=");
    builder.append(acPairs);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((acPairs == null) ? 0 : acPairs.hashCode());
    result = prime * result + ((question == null) ? 0 : question.hashCode());
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
    QaPairStat other = (QaPairStat) obj;
    if (acPairs == null) {
      if (other.acPairs != null)
        return false;
    } else if (!acPairs.equals(other.acPairs))
      return false;
    if (question == null) {
      if (other.question != null)
        return false;
    } else if (!question.equals(other.question))
      return false;
    return true;
  }


}
