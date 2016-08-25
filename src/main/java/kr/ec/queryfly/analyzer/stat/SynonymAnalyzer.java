package kr.ec.queryfly.analyzer.stat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

public class SynonymAnalyzer {

  public boolean isSimilar(String control, String experimental) {
    return experiment(control, experimental);
  }

  public boolean experiment(String control, String experimental) {

    String resultControl = control.chars().mapToObj(i -> (char) i)
        .filter(c -> Character.isLetter(c) || Character.isWhitespace(c)).map(c -> c.toString())
        .collect(Collectors.joining());

    System.out.println(resultControl);
    List<String> stringList = Splitter.on(" ").splitToList(resultControl);

    Map<String, Integer> map = new HashMap<>();
    for (String e : stringList) {
      if (!e.equals(" ") && !e.equals("")) {
        if (map.containsKey(e)) {
          int num = map.get(e);
          map.put(e, num + 1);
        } else {
          map.put(e, 1);
        }
        System.out.println(e);
      }

    }

    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println(entry.getKey() + "/" + entry.getValue());
    }

    return false;

  }

  public Map<String, Integer> getFrequentWords(String s) {
    Map<String, Integer> map = trimAndMap(s);
    map = Maps.filterValues(map, v -> v > 2);

    return map;

  }

  public MapDifference<String, Integer> diffString(String a, String b) {
    String trimA = a.chars().mapToObj(i -> (char) i)
        .filter(c -> Character.isLetter(c) || Character.isWhitespace(c)).map(c -> c.toString())
        .collect(Collectors.joining());

    System.out.println(trimA);

    Map<String, Integer> mapA = trimAndMap(a);

    for (Map.Entry<String, Integer> entry : mapA.entrySet()) {
      System.out.println(entry.getKey() + "/" + entry.getValue());
    }


    Map<String, Integer> mapB = trimAndMap(b);
    for (Map.Entry<String, Integer> entry : mapB.entrySet()) {
      System.out.println(entry.getKey() + "/" + entry.getValue());
    }

    return Maps.difference(mapA, mapB);


  }

  private Map<String, Integer> trimAndMap(String s) {
    String trimS = s.chars().mapToObj(i -> (char) i)
        .filter(c -> Character.isLetter(c) || Character.isWhitespace(c)).map(c -> c.toString())
        .collect(Collectors.joining());

    List<String> stringList = Splitter.on(" ").splitToList(trimS);

    Map<String, Integer> map = new HashMap<>();
    for (String e : stringList) {
      if (!e.equals(" ") && !e.equals("")) {
        if (map.containsKey(e)) {
          int num = map.get(e);
          map.put(e, num + 1);
        } else {
          map.put(e, 1);
        }
      }

    }
    return map;
  }

}
