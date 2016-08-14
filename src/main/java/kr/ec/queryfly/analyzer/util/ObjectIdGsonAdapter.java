package kr.ec.queryfly.analyzer.util;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ObjectIdGsonAdapter extends TypeAdapter<ObjectId> {

  @Override
  public void write(JsonWriter out, ObjectId value) throws IOException {
    out.value(value.toHexString());
  }

  @Override
  public ObjectId read(JsonReader in) throws IOException {
    String hex = in.nextString();
    return new ObjectId(hex);
  }

}
