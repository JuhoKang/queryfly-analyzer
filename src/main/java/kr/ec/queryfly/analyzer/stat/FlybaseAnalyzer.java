package kr.ec.queryfly.analyzer.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.util.CharArrayMap.EntrySet;
import org.springframework.stereotype.Component;

import kr.ec.queryfly.analyzer.model.AcPair;
import kr.ec.queryfly.analyzer.model.AnswerOption;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;

/**
 * This name is not good.<br>
 * Analyzes Flies
 * 
 * @author Juho Kang
 *
 */

@Component
public class FlybaseAnalyzer {

  public Map<String, List<AcPair>> analyzeStat(Iterable<Fly> flies) {

    return sortToQACPairs(calcQaPairs(flies));

  }

  public Map<QaPair, Integer> getQueryInfo(Iterable<Fly> flies) {

    Map<QaPair, Integer> result = new HashMap<QaPair, Integer>();

    for (Fly e : flies) {
      for (QaPair pair : e.getQaPairs()) {
        QaPair woAnswer = QaPair.Builder.from(pair).removeAnswer().build();
        if (result.containsKey(woAnswer)) {
          int num = result.get(woAnswer);
          num++;
          result.put(woAnswer, num);
        } else {
          result.put(woAnswer, 1);
        }
      }
    }

    return result;
  }

  // need algorithm when there are no options.
  public Map<QaPair, Integer> calcQaPairs(Iterable<Fly> flies) {

    Map<QaPair, Integer> result = new HashMap<QaPair, Integer>();

    for (Fly e : flies) {
      for (QaPair pair : e.getQaPairs()) {
        String option = pair.getAnswerOption().getOption();
        if (option.startsWith("select-")) {
          if (result.containsKey(pair)) {
            int num = result.get(pair);
            num++;
            result.put(pair, num);
          } else {
            result.put(pair, 1);
          }
        } else if (option.startsWith("multi-")) {
          if (result.containsKey(pair)) {
            int num = result.get(pair);
            num++;
            result.put(pair, num);
          } else {
            result.put(pair, 1);
          }
        } else {
          // short, long
        }
      }

    }

    return result;
  }


  public Map<String, List<AcPair>> sortToQACPairs(Map<QaPair, Integer> qaPairStat) {

    // key represents questions
    Map<String, List<AcPair>> result = new HashMap<>();

    for (Map.Entry<QaPair, Integer> entry : qaPairStat.entrySet()) {
      String question = entry.getKey().getQuestion();
      String answer = entry.getKey().getAnswer();
      int count = entry.getValue().intValue();

      if (result.containsKey(question)) {
        List<AcPair> temp = result.get(question);
        temp.add(new AcPair(answer, count));
        result.put(question, temp);
      } else {
        List<AcPair> temp = new ArrayList<AcPair>();
        temp.add(new AcPair(answer, count));
        result.put(question, temp);
      }

    }

    return result;

  }

  public Map<QaPair, Integer> findByQuestion(String question, Map<QaPair, Integer> info) {
    Map<QaPair, Integer> result = new HashMap<>();

    for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
      if (question.equals(entry.getKey().getQuestion())) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    return result;
  }

  /**
   * 
   * @param question
   * @param info
   * @return
   */
  public AnswerOption assumeOption(Map<QaPair, Integer> info) {

    // logic to assume the questions' option.

    // first assume its a "short"
    AnswerOption.Builder optionBuilder = new AnswerOption.Builder("short");

    int uniqueAnswers = info.entrySet().size();

    // If the number of unique answers is below 10 assume its a "select"
    if (info.entrySet().size() < 10) {

      String option = "select";

      List<String> answerPool = new ArrayList<>();

      for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
        if (entry.getKey().getAnswer().length() > 100) {
          optionBuilder = new AnswerOption.Builder("long");
          break;
        }
      }

      for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
        if (entry.getKey() == null || entry.getKey().getAnswer().equals("")) {
          uniqueAnswers--;
        } else {
          answerPool.add(entry.getKey().getAnswer());
        }
      }

      return new AnswerOption.Builder(option + "-" + uniqueAnswers).answerPool(answerPool).build();

    } else {

      // TODO extract answerpool from multi.
      int multiPotential = 0;

      // If There is a answer longer than 100, its a "long"
      // If there are answers with a comma and has duplicate answers multiPotential++
      for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
        if (entry.getKey().getAnswer().contains(",") && entry.getValue() > 1) {
          multiPotential++;
        }
      }

      if (multiPotential > 3) {
        optionBuilder = new AnswerOption.Builder("multi");
      }

    }

    return optionBuilder.build();

  }

  // TODO
  public void assumeKeywords() {

  }


}
