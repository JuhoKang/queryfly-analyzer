package kr.ec.queryfly.analyzer.data.util;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.AnswerOption;

@Component
public class AnswerOptionReadConverter
    implements Converter<DBObject, AnswerOption> {
  // lacks validation
  @SuppressWarnings("unchecked")
  @Override
  public AnswerOption convert(DBObject source) {

    AnswerOption ao;
    if (source.containsField("answerPool")) {
      ao = new AnswerOption.Builder(source.get("option").toString()).build();
    } else {
      //unchecked for casting to List<String>
      ao = new AnswerOption.Builder(source.get("option").toString())
          .answerPool(((List<String>) source.get("answerPool"))).build();
    }

    return ao;
  }

}
