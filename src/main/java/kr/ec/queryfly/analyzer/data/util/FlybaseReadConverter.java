package kr.ec.queryfly.analyzer.data.util;

import java.time.ZonedDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Flybase;

@ReadingConverter
@Component
public class FlybaseReadConverter implements Converter<DBObject, Flybase> {

  @Override
  public Flybase convert(DBObject source) {
    Flybase flybase = new Flybase.Builder(source.get("name").toString())
        .id(source.get("_id").toString())
        .description(source.get("description").toString())
        .createTime(ZonedDateTime.parse(source.get("createTime").toString()))
        .build();
    return flybase;
  }

}
