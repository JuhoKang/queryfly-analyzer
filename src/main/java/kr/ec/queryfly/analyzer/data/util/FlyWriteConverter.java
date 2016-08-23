package kr.ec.queryfly.analyzer.data.util;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;;

@WritingConverter
@Component
public class FlyWriteConverter implements Converter<Fly, DBObject> {

  @Autowired
  QaPairWriteConverter qpwConverter;

  @Override
  public DBObject convert(Fly source) {

    DBObject dbo = new BasicDBObject();
    if (source.getId() != null) {
      dbo.put("_id", source.getId());
    }

    dbo.put("flybaseId", source.getFlybaseId());

    if (source.getCreateTime() != null) {
      dbo.put("flybaseId", source.getCreateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

    List<DBObject> list = new ArrayList<DBObject>();
    for (QaPair item : source.getQaPairs()) {
      list.add(qpwConverter.convert(item));
      dbo.put("qaPairs", list);
    }

    return dbo;
  }

}
