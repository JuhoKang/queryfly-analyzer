package kr.ec.queryfly.analyzer.model;

import java.util.List;

public class AnswerOption {

  private final String option;
  private final List<String> answerPool;

  public String getOption() {
    return option;
  }

  public List<String> getAnswerPool() {
    return answerPool;
  }

  private AnswerOption(Builder builder) {
    this.option = builder.option;
    this.answerPool = builder.answerPool;
  }


  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("AnswerOption [option=");
    builder2.append(option);
    builder2.append(", answerPool=");
    builder2.append(answerPool);
    builder2.append("]");
    return builder2.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((answerPool == null) ? 0 : answerPool.hashCode());
    result = prime * result + ((option == null) ? 0 : option.hashCode());
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
    AnswerOption other = (AnswerOption) obj;
    if (answerPool == null) {
      if (other.answerPool != null)
        return false;
    } else if (!answerPool.equals(other.answerPool))
      return false;
    if (option == null) {
      if (other.option != null)
        return false;
    } else if (!option.equals(other.option))
      return false;
    return true;
  }



  public static class Builder {
    private final String option;
    private List<String> answerPool;

    public Builder(String option) {
      if (option == null) {
        throw new IllegalArgumentException("option can't be null");
      }
      this.option = option;

    }

    public Builder answerPool(List<String> answerPool) {
      this.answerPool = answerPool;
      return this;
    }

    public AnswerOption build() {
      return new AnswerOption(this);
    }

  }

}
