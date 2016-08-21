package kr.re.ec.queryfly.analyzer.kormorpheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Splitter;

public class KorMorphemeTest {

  @Test
  public void testDefault() {
    String input = "";
    input = input.replace(",", " ");
    input = input.replace(")", " ");
    input = input.replace(".", " ");
    input = input.replace("(", " ");
    input = input.replace("…", " ");
    input = input.replace("!", " ");
    input = input.replace("?", " ");

    List<String> stringList = Splitter.on(" ").splitToList(input);

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
      System.out.println(entry.getKey()+"/"+entry.getValue());
    }


  }
}
