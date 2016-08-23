package kr.ec.queryfly.analyzer.model;

public class AvPair {

  private final String answer;
  private final int value;

  public String getAnswer() {
    return answer;
  }

  public int getValue() {
    return value;
  }

  public AvPair(String answer, int value) {
    super();
    this.answer = answer;
    this.value = value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((answer == null) ? 0 : answer.hashCode());
    result = prime * result + value;
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
    AvPair other = (AvPair) obj;
    if (answer == null) {
      if (other.answer != null)
        return false;
    } else if (!answer.equals(other.answer))
      return false;
    if (value != other.value)
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AvPair [answer=");
    builder.append(answer);
    builder.append(", value=");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

}
