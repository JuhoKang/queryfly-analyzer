package kr.ec.queryfly.analyzer.util;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component("prettyGson")
public class PrettyGsonUtil implements GsonUtil{
  
  private final Gson gson;
  
  public PrettyGsonUtil() {
    gson = new GsonBuilder().setPrettyPrinting().create();
  }
  
  public Gson getGson(){
    return this.gson;
  }

  @Override
  public String toJson(Object src) {
    return this.gson.toJson(src);
  }

}
