package kr.ec.queryfly.analyzer.data.util;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.AnswerOption;

@ReadingConverter
@Component
public class AnswerOptionReadConverter implements Converter<DBObject, AnswerOption> {
  // lacks validation
  @SuppressWarnings("unchecked")
  @Override
  public AnswerOption convert(DBObject source) {

    AnswerOption ao;
    if (source.containsField("answerPool")) {
      // unchecked for casting to List<String>
      ao = new AnswerOption.Builder(source.get("option").toString())
          .answerPool(((List<String>) source.get("answerPool"))).build();
    } else {
      ao = new AnswerOption.Builder(source.get("option").toString()).build();
    }

    return ao;
  }

}
