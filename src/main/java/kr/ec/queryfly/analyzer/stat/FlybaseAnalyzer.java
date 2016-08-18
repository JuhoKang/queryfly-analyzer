package kr.ec.queryfly.analyzer.stat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

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

  public final static String Q_A_SEPERATOR = "#%$";

  public Map<String, String> analyze(Iterable<Fly> flies) {
    Map<String, String> result = new HashMap<String, String>();
    Map<String, Integer> resultStrInt = new HashMap<String, Integer>();

    // trash but works tightly coupled with FlybaseApiService --> need to be changed.
    for (Fly e : flies) {
      for (QaPair pair : e.getQaPairs()) {
        if (pair.getAnswerOption().getOption().startsWith("select-")) {
          if (resultStrInt.containsKey(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer())) {
            int num = resultStrInt.get(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer());
            num++;
            resultStrInt.put(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer(), num);
          } else {
            resultStrInt.put(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer(), 1);
          }
        } else if (pair.getAnswerOption().getOption().startsWith("multi-")) {
          if (resultStrInt.containsKey(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer())) {
            int num = resultStrInt.get(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer());
            num++;
            resultStrInt.put(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer(), num);
          } else {
            resultStrInt.put(pair.getQuestion() + Q_A_SEPERATOR + pair.getAnswer(), 1);
          }
        } else {
          // short, long
        }
      }

    }

    for (Map.Entry<String, Integer> entry : resultStrInt.entrySet()) {
      result.put(entry.getKey(), entry.getValue().toString());
    }


    return result;

  }

}
