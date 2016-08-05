package kr.ec.queryfly.analyzer.util;

import com.google.gson.Gson;

public interface GsonUtil {

  Gson getGson();
  
  String toJson(Object src);
  
}
