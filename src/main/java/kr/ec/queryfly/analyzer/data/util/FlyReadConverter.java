package kr.ec.queryfly.analyzer.data.util;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;


@ReadingConverter
@Component
public class FlyReadConverter implements Converter<DBObject, Fly> {

  @Autowired
  QaPairReadConverter qprConverter;

  @Override
  public Fly convert(DBObject source) {
    @SuppressWarnings("unchecked")
    List<DBObject> list = (List<DBObject>) source.get("qaPairs");
    List<QaPair> qaPairs =
        list.stream().map(item -> qprConverter.convert(item)).collect(Collectors.toList());

    Fly.Builder builder = new Fly.Builder(qaPairs);

    builder = builder.flybaseId((ObjectId) source.get("flybaseId"));

    builder = builder.id(new ObjectId(source.get("_id").toString()));

    if (source.containsField("createTime")) {
      builder = builder.createTime(ZonedDateTime.parse(source.get("createTime").toString()));
    }

    return builder.build();
  }

}
