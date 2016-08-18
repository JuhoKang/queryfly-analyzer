package kr.ec.queryfly.analyzer.model;

import com.mongodb.annotations.Immutable;

@Immutable
public class QaPair {

  private final String question;
  private final AnswerOption answerOption;
  private final String answer;

  public String getQuestion() {
    return question;
  }

  public AnswerOption getAnswerOption() {
    return answerOption;
  }

  public String getAnswer() {
    return answer;
  }

  private QaPair(Builder builder) {
    this.question = builder.question;
    this.answerOption = builder.answerOption;
    this.answer = builder.answer;
  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("QaPair [question=");
    builder2.append(question);
    builder2.append(", answerOption=");
    builder2.append(answerOption);
    builder2.append(", answer=");
    builder2.append(answer);
    builder2.append("]");
    return builder2.toString();
  }

  public static class Builder {

    private final String question;
    private AnswerOption answerOption;
    private String answer;

    public Builder(String question) {
      if (question == null) {
        throw new IllegalArgumentException("question can't be null");
      }
      this.question = question;
    }

    public Builder answerOption(AnswerOption answerOption) {
      this.answerOption = answerOption;
      return this;
    }

    public Builder answer(String answer) {
      this.answer = answer;
      return this;
    }

    public QaPair build() {
      return new QaPair(this);
    }

  }

  // equality and hashcode is only based on the question and answer
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((answer == null) ? 0 : answer.hashCode());
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
    QaPair other = (QaPair) obj;
    if (answer == null) {
      if (other.answer != null)
        return false;
    } else if (!answer.equals(other.answer))
      return false;
    if (question == null) {
      if (other.question != null)
        return false;
    } else if (!question.equals(other.question))
      return false;
    return true;
  }



}
