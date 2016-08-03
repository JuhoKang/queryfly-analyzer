package kr.ec.queryfly.analyzer.data.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.AnswerOption;

@Component
public class AnswerOptionWriteConverter
    implements Converter<AnswerOption, DBObject> {

  @Override
  public DBObject convert(AnswerOption source) {
    DBObject dbo = new BasicDBObject();
    dbo.put("answerPool", source.getAnswerPool());
    dbo.put("option", source.getOption());
    return dbo;
  }

}
