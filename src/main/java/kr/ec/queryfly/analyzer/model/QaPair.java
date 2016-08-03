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

}
