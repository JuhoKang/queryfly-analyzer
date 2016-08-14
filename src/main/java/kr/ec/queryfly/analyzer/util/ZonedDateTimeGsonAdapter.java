package kr.ec.queryfly.analyzer.util;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ZonedDateTimeGsonAdapter extends TypeAdapter<ZonedDateTime> {

  @Override
  public void write(JsonWriter out, ZonedDateTime value) throws IOException {
    out.value(value.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
  }

  @Override
  public ZonedDateTime read(JsonReader in) throws IOException {
    String time = in.nextString();
    return ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

}
