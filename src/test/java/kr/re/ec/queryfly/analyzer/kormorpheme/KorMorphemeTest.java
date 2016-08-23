package kr.re.ec.queryfly.analyzer.kormorpheme;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.collect.MapDifference;
import com.google.common.io.Files;

import kr.ec.queryfly.analyzer.stat.SynonymAnalyzer;

public class KorMorphemeTest {

  @Test
  public void testDefault() throws IOException, URISyntaxException {

    SynonymAnalyzer syn = new SynonymAnalyzer();

    MapDifference<String, Integer> mapA =
        syn.diffString("1.하루에 담배 담배 보통 몇 개비나 피우십니까?", "1.하루에 보통 몇 개비나 피우십니까?");

    MapDifference<String, Integer> mapB =
        syn.diffString("2.아침에 일어나서 얼마 만에 첫 담배를 피우십니까?", "4.하루중 담배 맛이 가장 좋은 때는 언제입니까?");


    for (Map.Entry<String, Integer> entry : mapA.entriesInCommon().entrySet()) {
      System.out.println("mapA    " + entry.getKey() + "/" + entry.getValue());
    }

    for (Map.Entry<String, Integer> entry : mapB.entriesInCommon().entrySet()) {
      System.out.println("mapB    " + entry.getKey() + "/" + entry.getValue());
    }

    String case1 = null;

    case1 = Files.toString(new File(this.getClass().getResource("/InputCase1").toURI()),
        Charsets.UTF_8);

    String[] split = case1.split("&");
    Map<String, Integer> result = syn.getFrequentWords(split[0]);
    for (Map.Entry<String, Integer> entry : result.entrySet()) {
      System.out.println("result    " + entry.getKey() + "/" + entry.getValue());
    }

  }
}
