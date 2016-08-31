package kr.ec.queryfly.analyzer.data.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.QaPair;

@WritingConverter
@Component
public class QaPairWriteConverter implements Converter<QaPair, DBObject> {

  @Autowired
  AnswerOptionWriteConverter aowConverter;

  @Override
  public DBObject convert(QaPair source) {
    DBObject dbo = new BasicDBObject();

    dbo.put("question", source.getQuestion());

    if (source.getAnswer() != null) {
      dbo.put("answer", source.getAnswer());
    }

    if (source.getAnswerOption() != null) {
      dbo.put("answerOption", aowConverter.convert(source.getAnswerOption()));
    }

    return dbo;
  }

}
