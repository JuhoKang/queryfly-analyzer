package kr.ec.queryfly.analyzer.stat;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import kr.ec.queryfly.analyzer.model.AnswerOption;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;

public class FlybaseAnalyzerTest {

  private FlybaseAnalyzer analyzer;

  @Before
  public void setUp() {
    analyzer = new FlybaseAnalyzer();
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testAnalyze() {

    List<Fly> flies = new ArrayList<>();

    analyzer.analyzeStat(flies);

  }

  @Test
  public void testQaPairEqualsOnlyByQA() {
    QaPair qp1 = new QaPair.Builder("question").answer("answer").build();
    QaPair qp2 = new QaPair.Builder("question")
        .answerOption(new AnswerOption.Builder("option").build()).answer("answer").build();

    assertEquals(qp1, qp2);
    assertEquals(qp1.hashCode(), qp2.hashCode());
  }

  @Test
  public void testCalcQaPairs() {

    List<QaPair> qaPairs = new ArrayList<QaPair>();
    for (int i = 1; i < 10; i++) {
      QaPair qp = new QaPair.Builder("question" + 10 / i)
          .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 2 / i)
          .build();
      System.out.println(qp);
      qaPairs.add(qp);
    }

    QaPair qp102 = new QaPair.Builder("question" + 10)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 2).build();
    QaPair qp51 = new QaPair.Builder("question" + 5)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 1).build();
    QaPair qp30 = new QaPair.Builder("question" + 3)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp20 = new QaPair.Builder("question" + 2)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp10 = new QaPair.Builder("question" + 1)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp11 = new QaPair.Builder("question" + 1)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 1).build();

    qaPairs.add(qp11);
    qaPairs.add(qp11);
    // two adds

    Fly fly1 = new Fly.Builder(qaPairs).flybaseId(new ObjectId()).build();
    List<Fly> flies = new ArrayList<Fly>();
    flies.add(fly1);

    Map<QaPair, Integer> result = analyzer.calcQaPairs(flies);

    assertEquals(1, result.get(qp102).intValue());
    assertEquals(1, result.get(qp51).intValue());
    assertEquals(1, result.get(qp30).intValue());
    assertEquals(2, result.get(qp20).intValue());
    assertEquals(4, result.get(qp10).intValue());
    assertEquals(2, result.get(qp11).intValue());

  }

  @Test
  public void testGetQueryInfo() {

  }

  @Test
  public void testAssumeKeyword() {
    String jsonCase = null;
    try {
      jsonCase = Files.toString(new File(this.getClass().getResource("/InputCase1").toURI()),
          Charsets.UTF_8);
    } catch (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    List<String> result = null;
    result = analyzer.assumeKeywords(jsonCase);

    for (String s : result) {
      System.out.println("keyword : " + s);
    }
  }


  @Test
  public void testSortToQacPairs() {}

  @Test
  public void testAssumeOption() {

  }

  public List<Fly> makeTestFlies() {
    List<QaPair> qaPairs = new ArrayList<QaPair>();
    for (int i = 1; i < 10; i++) {
      QaPair qp = new QaPair.Builder("question" + 10 / i)
          .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 2 / i)
          .build();
      System.out.println(qp);
      qaPairs.add(qp);
    }

    QaPair qp102 = new QaPair.Builder("question" + 10)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 2).build();
    QaPair qp51 = new QaPair.Builder("question" + 5)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 1).build();
    QaPair qp30 = new QaPair.Builder("question" + 3)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp20 = new QaPair.Builder("question" + 2)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp10 = new QaPair.Builder("question" + 1)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 0).build();
    QaPair qp11 = new QaPair.Builder("question" + 1)
        .answerOption(new AnswerOption.Builder("select-4").build()).answer("answer" + 1).build();

    qaPairs.add(qp11);
    qaPairs.add(qp11);
    // two adds

    Fly fly1 = new Fly.Builder(qaPairs).flybaseId(new ObjectId()).build();
    List<Fly> flies = new ArrayList<Fly>();
    flies.add(fly1);

    return flies;
  }

}
