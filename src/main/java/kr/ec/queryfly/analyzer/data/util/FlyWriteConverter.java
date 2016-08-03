package kr.ec.queryfly.analyzer.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;;

@Component
public class FlyWriteConverter implements Converter<Fly, DBObject> {

  @Autowired
  QaPairWriteConverter qpwConverter;

  @Override
  public DBObject convert(Fly source) {
    
    if(qpwConverter == null){
      System.out.println("converter is null");
    }
    
    DBObject dbo = new BasicDBObject();
    if (source.getId() != null) {
      System.out.println("here");
      dbo.put("_id", source.getId());
    }
    System.out.println("here2");
    List<DBObject> list = new ArrayList<DBObject>();
    for (QaPair item : source.getQaPairs()) {
      list.add(qpwConverter.convert(item));
      dbo.put("qaPairs", list);
    }
    return dbo;
  }

}
