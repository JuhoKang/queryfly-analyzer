package kr.re.ec.queryfly.analyzer.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.re.ec.queryfly.analyzer.core.SimpleApiService;

@Service("test")
public class Test extends SimpleApiService {

  @Override
  public String whenGet(Map<String, String> request)
      throws RequestParamException {
    JsonObject apiResult = new JsonObject();
    JsonArray array = new JsonArray();
    JsonObject QR = new JsonObject();
    QR.addProperty("Query", "나는 지금 지갑을 가지고 있다.");
    QR.addProperty("AnswerOption", "boolean");
    QR.addProperty("Answer", true);
    array.add(QR);
    JsonObject QR2 = new JsonObject();
    QR2.addProperty("Query", "나는 지금 시계를 차고 있다.");
    QR2.addProperty("AnswerOption", "boolean");
    QR2.addProperty("Answer", true);
    array.add(QR2);
    JsonObject QR3 = new JsonObject();
    QR3.addProperty("Query", "나는 지금 시계를 차고 있다.");
    QR3.addProperty("AnswerOption", "boolean");
    QR3.addProperty("Answer", true);
    array.add(QR3);
    JsonObject QR4 = new JsonObject();
    QR4.addProperty("Query", "나는 지금 시계를 차고 있다.");
    QR4.addProperty("AnswerOption", "boolean");
    QR4.addProperty("Answer", true);
    array.add(QR4);
    JsonObject QR5 = new JsonObject();
    QR5.addProperty("Query", "나는 지금 시계를 차고 있다.");
    QR5.addProperty("AnswerOption", "boolean");
    QR5.addProperty("Answer", true);
    array.add(QR5);
    JsonObject QR6 = new JsonObject();
    QR6.addProperty("Query", "나는 지금 시계를 차고 있다.");
    QR6.addProperty("AnswerOption", "boolean");
    QR6.addProperty("Answer", true);
    array.add(QR6);
    apiResult.add("QRarray", array);
    apiResult.addProperty("createTime", LocalDateTime.now().toString());
    return apiResult.toString();
  }

  @Override
  public String whenPost(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenPut(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

  @Override
  public String whenDelete(Map<String, String> request)
      throws RequestParamException {
    throw new RequestParamException();
  }

}
