package kr.ec.queryfly.analyzer.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

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

  private static final Logger logger = LoggerFactory.getLogger(FlybaseAnalyzer.class);

  public Map<String, List<AcPair>> analyzeStat(Iterable<Fly> flies) {

    return sortToQACPairs(calcQaPairs(flies));

  }

  public Map<QaPair, Integer> getQueryInfo(Iterable<Fly> flies) {

    Map<QaPair, Integer> qaPairInfo = calcQaPairs(flies);

    Map<QaPair, Integer> result = new HashMap<QaPair, Integer>();

    for (Fly e : flies) {
      for (QaPair pair : e.getQaPairs()) {
        QaPair.Builder woAnswerBuilder = QaPair.Builder.from(pair).removeAnswer();
        if (result.containsKey(woAnswerBuilder.build())) {
          int num = result.get(woAnswerBuilder.build());
          num++;
          result.put(woAnswerBuilder.build(), num);
        } else {

          Map<QaPair, Integer> questionInfo = findByQuestion(pair.getQuestion(), qaPairInfo);
          AnswerOption option = assumeOption(questionInfo);
          result.put(woAnswerBuilder.answerOption(option).build(), 1);
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
        if (pair.getAnswerOption() != null) {
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
        } else {
          if (result.containsKey(pair)) {
            int num = result.get(pair);
            num++;
            result.put(pair, num);
          } else {
            result.put(pair, 1);
          }
        }
      }

    }

    for (Map.Entry<QaPair, Integer> entry : result.entrySet()) {
      System.out.println("K : " + entry.getKey() + "/ V :" + entry.getValue());
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


    int multiPotential = 0;

    // If There is a answer longer than 100, its a "long"
    // If there are answers with a semi colon and has duplicate answers multiPotential++
    for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
      if (entry.getKey().getAnswer().contains(";") && entry.getValue() > 0) {
        multiPotential++;
      }
    }

    logger.debug("multiPotential is :" + multiPotential);
    if (multiPotential > 3) {
      // TODO extract answerpool from multi.
      Set<String> answerSet = new HashSet<>();
      for (Map.Entry<QaPair, Integer> entry : info.entrySet()) {
        String answer = entry.getKey().getAnswer();
        if (answer.contains(";")) {
          List<String> splittedList = Splitter.on(";").splitToList(answer);
          answerSet.addAll(splittedList);
        } else {
          answerSet.add(answer);
        }
      }
      // remove blanks
      answerSet.remove("");

      return new AnswerOption.Builder("multi-" + answerSet.size())
          .answerPool(new ArrayList<String>(answerSet)).build();

    }

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

    }


    return optionBuilder.build();

  }

  // TODO
  public void assumeKeywords() {

  }


}
