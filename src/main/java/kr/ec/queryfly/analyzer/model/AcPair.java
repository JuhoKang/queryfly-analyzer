package kr.ec.queryfly.analyzer.model;

public class AcPair {

  private final String answer;
  private final int count;

  public String getAnswer() {
    return answer;
  }

  public int getCount() {
    return count;
  }

  public AcPair(String answer, int count) {
    super();
    this.answer = answer;
    this.count = count;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((answer == null) ? 0 : answer.hashCode());
    result = prime * result + count;
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
    AcPair other = (AcPair) obj;
    if (answer == null) {
      if (other.answer != null)
        return false;
    } else if (!answer.equals(other.answer))
      return false;
    if (count != other.count)
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AcPair [answer=");
    builder.append(answer);
    builder.append(", count=");
    builder.append(count);
    builder.append("]");
    return builder.toString();
  }



}
