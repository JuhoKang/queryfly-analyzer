package kr.ec.queryfly.analyzer.data.util;

import java.time.ZonedDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Flybase;

@ReadingConverter
@Component
public class FlybaseReadConverter implements Converter<DBObject, Flybase> {

  @SuppressWarnings("unchecked")
  // unchecked for get(keywords)
  @Override
  public Flybase convert(DBObject source) {

    Flybase.Builder builder =
        new Flybase.Builder(source.get("name").toString()).id((ObjectId) source.get("_id"))
            .createTime(ZonedDateTime.parse(source.get("createTime").toString()));

    if (source.containsField("description")) {
      builder.description(source.get("description").toString());
    }
    if (source.containsField("keywords")) {
      builder.keywords((List<String>) source.get("keywords"));
    }
    return builder.build();
  }

}
