package kr.ec.queryfly.analyzer.data.util;

import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Flybase;

@WritingConverter
@Component
public class FlybaseWriteConverter implements Converter<Flybase, DBObject> {

  @Override
  public DBObject convert(Flybase source) {
    DBObject dbo = new BasicDBObject();
    if (source.getId() != null) {
      dbo.put("_id", source.getId());
    }

    dbo.put("name", source.getName());

    if (source.getDescription() != null) {
      dbo.put("description", source.getDescription());
    }

    if (source.getCreateTime() != null) {
      dbo.put("createTime",
          source.getCreateTime().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }


    return dbo;
  }

}
