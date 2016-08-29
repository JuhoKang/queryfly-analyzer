package kr.ec.queryfly.analyzer.web.service;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.AcPair;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.model.CustomMediaType;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.stat.CSVtoFliesConverter;
import kr.ec.queryfly.analyzer.stat.FlybaseAnalyzer;
import kr.ec.queryfly.analyzer.util.GsonUtil;
import kr.ec.queryfly.analyzer.util.JsonResult;

/**
 * needs further refactoring
 * 
 * @author Juho Kang
 *
 */
@Service("flybaseApiService")
public class FlybaseApiService extends SimpleCrudApiService {

  private final FlybaseRepository flybaseRepo;

  private final FlyRepository flyRepo;

  private final GsonUtil gson;

  private final FlybaseAnalyzer analyzer;

  private final MessageSource msg;

  @Autowired
  public FlybaseApiService(FlybaseRepository flybaseRepo, FlyRepository flyRepo, GsonUtil gson,
      FlybaseAnalyzer analyzer, MessageSource msg) {
    this.flybaseRepo = flybaseRepo;
    this.flyRepo = flyRepo;
    this.gson = gson;
    this.analyzer = analyzer;
    this.msg = msg;
  }

  @Override
  public String whenGet(ApiRequest request) throws RequestParamException {
    List<String> uriElements = getSplittedPath(request.getUri());
    /*
     * for (Map.Entry<String, String> entry : request.entrySet()) {
     * System.out.println(entry.getKey() + "/" + entry.getValue()); }
     */

    // default value
    int page = 0;
    int perPage = 30;
    if (request.getParameters() != null) {
      if (request.getParameters().containsKey("page")) {
        // pagination is zero-based.
        page = Integer.parseInt(request.getParameters().get("page").get(0)) - 1;
      }
      if (request.getParameters().containsKey("per_page")) {
        perPage = Integer.parseInt(request.getParameters().get("per_page").get(0));
      }

    }

    // ("/flybase")
    if (uriElements.size() == 1) {
      Page<Flybase> flybasePage = flybaseRepo.findAll(new PageRequest(page, perPage));
      if (!flybasePage.hasContent()) {
        return new JsonResult().noValue();
      }

      return gson.toJson(ImmutableList.copyOf(flybasePage));

      // ("/flybase/{id}")
    } else if (uriElements.size() == 2) {
      String flybaseId = uriElements.get(1);
      if (!ObjectId.isValid(flybaseId)) {
        throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
      }

      Page<Fly> flyPage =
          flyRepo.findByFlybaseId(new ObjectId(flybaseId), new PageRequest(page, perPage));

      if (!flyPage.hasContent()) {
        return new JsonResult().noValue();
      }
      return gson.toJson(ImmutableList.copyOf(flyPage));

      // ("/flybase/{id}/stat")
      // ("/flybase/{id}/query")
    } else if (uriElements.size() == 3) {
      String flybaseId = uriElements.get(1);
      String operation = uriElements.get(2);

      if (!ObjectId.isValid(flybaseId)) {
        throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
      }

      if (operation.equals("stat")) {

        // should change pageRequestThing
        Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseId), new PageRequest(0, 200));

        return rawStatToJson(analyzer.analyzeStat(flies));

      } else if (operation.equals("query")) {

        Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseId), new PageRequest(0, 200));

        return gson.toJson(analyzer.getQueryInfo(flies));

      } else {
        throw new RequestParamException(
            defaultMsgArg("error.arg.nooperation", "/flybase/{id}/(operation) GET."));
      }
    } else {
      throw new RequestParamException(defaultMsgArg("error.arg.notvalidrequest", "/flybase GET"));
    }
    // return new JsonResult().noValue();
  }

  @Override
  public String whenPost(ApiRequest request) throws RequestParamException {
    List<String> uriElements = getSplittedPath(request.getUri());


    // ("/flybase")
    if (uriElements.size() == 1) {
      if (!request.getPostFormData().containsKey("name")
          || !request.getPostFormData().containsKey("description")) {
        throw new RequestParamException(
            defaultMsgArg("error.arg.missingarg", "POSTarg/name, POSTarg/description"));
      }

      Flybase.Builder builder = new Flybase.Builder(request.getPostFormData().get("name"))
          .description(request.getPostFormData().get("description"))
          .createTime(ZonedDateTime.now());

      if (request.getPostFormData().containsKey("keywords")) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> keywords =
            gson.getGson().fromJson(request.getPostFormData().get("keywords"), listType);
        /*
         * for (String s : keywords) { System.out.println("show keywords :" + s); }
         */
        builder.keywords(keywords);
      }

      Flybase flybase = builder.build();
      Flybase resultFlybase = flybaseRepo.save(flybase);
      return gson.toJson(resultFlybase);

      // ("/flybase/{id}")
    } else if (uriElements.size() == 2) {
      String flybaseId = uriElements.get(1);
      if (!ObjectId.isValid(flybaseId)) {
        throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
      }
      if (!request.getHeaders().containsKey(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)) {
        throw new RequestParamException(defaultMsg("error.nocontenttype"));
      }

      if (request.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)
          .contains(CustomMediaType.APPLICATION_CSV_VALUE)) {
        String csvInput = request.getPostRawData();
        CSVtoFliesConverter converter = new CSVtoFliesConverter();
        List<Fly> flies = converter.parse(csvInput, new ObjectId(flybaseId));
        List<Fly> resultFlies = flyRepo.save(flies);
        return gson.toJson(resultFlies);

      } else if (request.getHeaders().get(CustomMediaType.HEADER_CONTENT_TYPE_VALUE)
          .contains(CustomMediaType.APPLICATION_JSON_VALUE)) {
        // TODO
        return "";

      } else {
        throw new RequestParamException(defaultMsg("error.notsupportedcontenttype"));
      }

    } else {
      throw new RequestParamException(defaultMsgArg("error.arg.notvalidrequest", "/flybase POST"));
    }

  }

  @Override
  public String whenPut(ApiRequest request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String whenDelete(ApiRequest request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }


  private String rawStatToJson(Map<String, List<AcPair>> rawStat) {
    JsonObject obj = new JsonObject();
    JsonArray array = new JsonArray();

    for (Map.Entry<String, List<AcPair>> entry : rawStat.entrySet()) {
      JsonObject tempObj = new JsonObject();
      tempObj.addProperty("question", entry.getKey());
      JsonArray tempArray = new JsonArray();
      for (AcPair pair : entry.getValue()) {
        JsonObject answerCount = new JsonObject();
        answerCount.addProperty("answer", pair.getAnswer());
        answerCount.addProperty("count", pair.getCount());
        tempArray.add(answerCount);
      }
      tempObj.add("answerCounts", tempArray);
      array.add(tempObj);
    }
    obj.add("qaStats", array);

    return obj.toString();
  }

  private String defaultMsg(String key) {
    return msg.getMessage(key, null, Locale.KOREA);
  }

  private String defaultMsgArg(String key, String arg) {
    return msg.getMessage(key + arg, null, Locale.KOREA);
  }


}
