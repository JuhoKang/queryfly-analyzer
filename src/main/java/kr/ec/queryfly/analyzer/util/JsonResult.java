package kr.ec.queryfly.analyzer.util;

import com.google.gson.JsonObject;

import io.netty.handler.codec.http.HttpResponseStatus;

public class JsonResult {
  
  public static final String STATUS_PROPERTY = "code";
  public static final String DESCRIPTION = "description";
  
  public String noValue(){
    JsonObject obj = new JsonObject();
    return obj.toString();
  }
  
  public String httpResult(int code, String description){
    JsonObject obj = new JsonObject();
    obj.addProperty(STATUS_PROPERTY, HttpResponseStatus.valueOf(code).code());
    obj.addProperty(DESCRIPTION, description);
    return obj.toString();
  }
  
}
