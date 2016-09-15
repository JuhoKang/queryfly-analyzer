package kr.ec.queryfly.analyzer.stat;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
  public List<String> assumeKeywords(String source) {
    List<String> result = new ArrayList<>();
    Map<String, Integer> wordMap = new HashMap<>();
    KoreanAnalyzer analyzer = new KoreanAnalyzer();
    TokenStream stream = analyzer.tokenStream("tempstream", new StringReader(source));

    CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
    try {
      stream.reset();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    try {
      while (stream.incrementToken()) {
        if (wordMap.containsKey(termAtt.toString())) {
          int num = wordMap.get(termAtt.toString());
          num++;
          wordMap.put(termAtt.toString(), num);
        } else {
          wordMap.put(termAtt.toString(), 1);
        }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    analyzer.close();
    try {
      stream.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ValueComparator<String, Integer> comparator = new ValueComparator<String, Integer>(wordMap);
    TreeMap<String, Integer> reverse =
        new TreeMap<String, Integer>(new ReverseComparator<String>(comparator));
    reverse.putAll(wordMap);
    logger.debug("" + reverse);

    if (reverse.size() < 5) {
      for (int i = 0; i < reverse.size(); i++) {
        result.add(reverse.pollFirstEntry().getKey());
      }
    } else {
      for (int i = 0; i < 5; i++) {
        result.add(reverse.pollFirstEntry().getKey());
      }
    }

    return result;
  }

  private static class ValueComparator<K extends Comparable<K>, V extends Comparable<V>>
      implements Comparator<K> {
    private Map<K, V> map;

    ValueComparator(Map<K, V> map) {
      this.map = map;
    }

    public int compare(K o1, K o2) {
      int p = map.get(o1).compareTo(map.get(o2));
      if (p != 0) {
        return p;
      }
      return o1.compareTo(o2);
    }
  }
  private static class ReverseComparator<T> implements Comparator<T> {
    private Comparator<T> comparator;

    ReverseComparator(Comparator<T> comparator) {
      this.comparator = comparator;
    }

    public int compare(T o1, T o2) {
      return -1 * comparator.compare(o1, o2);
    }
  }


}
