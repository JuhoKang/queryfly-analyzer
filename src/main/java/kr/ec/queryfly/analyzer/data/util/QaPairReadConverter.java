package kr.ec.queryfly.analyzer.data.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.QaPair;


@ReadingConverter
@Component
public class QaPairReadConverter implements Converter<DBObject, QaPair> {

  @Autowired
  AnswerOptionReadConverter aorConverter;

  // needs validation + case handling
  @Override
  public QaPair convert(DBObject source) {
    QaPair qaPair = new QaPair.Builder(source.get("question").toString())
        .answerOption(
            aorConverter.convert((DBObject) source.get("answerOption")))
        .answer(source.get("answer").toString()).build();
    return qaPair;
  }

}
