package kr.ec.queryfly.analyzer.util;

import com.google.gson.JsonObject;

import io.netty.handler.codec.http.HttpResponseStatus;

public class JsonHttpStatus {
  
  public static final String STATUS_PROPERTY = "resultCode";
  
  public String getJsonStatus(int code){
    JsonObject obj = new JsonObject();
    obj.addProperty(STATUS_PROPERTY, HttpResponseStatus.valueOf(code).code());
    return obj.toString();
  }
  
}
