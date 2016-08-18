package kr.ec.queryfly.analyzer.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kr.ec.queryfly.analyzer.model.AcPair;
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

  public Map<String, List<AcPair>> analyze(Iterable<Fly> flies) {

    return sortToQACPairs(calcQaPairs(flies));

  }

  // need algorithm when there are no options.
  public Map<QaPair, Integer> calcQaPairs(Iterable<Fly> flies) {

    Map<QaPair, Integer> result = new HashMap<QaPair, Integer>();

    for (Fly e : flies) {
      for (QaPair pair : e.getQaPairs()) {
        if (pair.getAnswerOption().getOption().startsWith("select-")) {
          if (result.containsKey(pair)) {
            int num = result.get(pair);
            num++;
            result.put(pair, num);
          } else {
            result.put(pair, 1);
          }
        } else if (pair.getAnswerOption().getOption().startsWith("multi-")) {
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


}
