package kr.ec.queryfly.analyzer.util;

import com.google.gson.JsonObject;

import io.netty.handler.codec.http.HttpResponseStatus;

public class JsonResult {

  private static final String STATUS_CODE = "code";
  private static final String DESCRIPTION = "description";
  private static final String STATUS_REASON = "reasonPhrase";

  public String noValue() {
    JsonObject obj = new JsonObject();
    return obj.toString();
  }

  public String httpResult(int code, String description) {
    JsonObject obj = new JsonObject();
    obj.addProperty(STATUS_CODE, HttpResponseStatus.valueOf(code).code());
    obj.addProperty(STATUS_REASON, HttpResponseStatus.valueOf(code).reasonPhrase());
    obj.addProperty(DESCRIPTION, description);
    return obj.toString();
  }

}
