package kr.re.ec.queryfly.analyzer.kormorpheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Splitter;

public class KorMorphemeTest {

  @Test
  public void testDefault() {
    String input =
        "제발 정보보호병 하라고 또 수정합니다. 개 꿀.누가 보면 EC 공지방이든 뭐든 올려줘요.2015 12월, 1월 입대 후임 각각 한명씩옴얘넨 정보보호병이 아님… 16년 부터는 사단으로도 정보보호병 가는데.. 이분들은 꿀은 아닐듯하지만 부대가서 엘리트 인정받을 수있으니… 복불복?!?. 2015 5월 4일 입대 후임 두명옴 대학교 ⇒ 산기대,대림대(어디학교?) 둘다 컴공 자격증 ⇒ 정보처리기사, CCNA 나이 24, 22 진심 정보보호병 사령부로 나면 개꿀이니까 좀 하세요 우리 EC분들 꿀릴 스펙 하나도 없음. 군단가도 개꿀이고... OCJP, CCNA 덤프 쌍두마차 자격증 중 하나 돈주고 따고 면접보고 오셈. 15-3기면 내 후임은 안되지만 16-1기는 가능성이씀=> 나가리 누가 궁금할까 싶긴 하지만… 기록에 의의를 두고 씁니다. 혹시몰라 컴공인데 또 누군가 가고싶을지도 모르지. 인터넷에 정보 더럽게없음. 찾아도 의미없다. //2015년 2월 입영 기준// 2배수로 서류 통과하고 면접을 봄 서류는 내 스펙이 1종보통, 컴활 2급, OCJP, 토익 900이상, 동아리 20개월 활동(ec라 정보보호동아리는 아님), 컴퓨터공학과 2학년 재학? ( 2학년 수료로 처리됬는지는 모르겠음. 아마 됬을거임 ) 위에서 중요한건. 전공관련 자격증/ 전공학과 임. 여기서 먼저 분류로 짜르기 때문에 최소 컴공 + 자격증 이 필요함.";
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
