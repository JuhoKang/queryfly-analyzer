package kr.ec.queryfly.analyzer.web.controller;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.AcPair;
import kr.ec.queryfly.analyzer.model.CustomMediaType;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.model.QaPair;
import kr.ec.queryfly.analyzer.stat.CSVtoFliesConverter;
import kr.ec.queryfly.analyzer.stat.FlybaseAnalyzer;
import kr.ec.queryfly.analyzer.util.GsonUtil;
import kr.ec.queryfly.analyzer.util.JsonResult;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;

/**
 * Created by juho on 16. 12. 21.
 */

@RestController
@RequestMapping("/flybase")
public class FlybaseController {

  private final FlybaseRepository flybaseRepo;

  private final FlyRepository flyRepo;

  private final GsonUtil gson;

  private final FlybaseAnalyzer analyzer;

  private final MessageSource msg;

  @Autowired
  public FlybaseController(FlybaseRepository flybaseRepo, FlyRepository flyRepo, GsonUtil gson,
                           FlybaseAnalyzer analyzer, MessageSource msg) {
    this.flybaseRepo = flybaseRepo;
    this.flyRepo = flyRepo;
    this.gson = gson;
    this.analyzer = analyzer;
    this.msg = msg;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getFlybaseList(@RequestParam(value = "search") Optional<String> search,
                               @RequestParam(value = "page") Optional<Integer> page,
                               @RequestParam(value = "per_page") Optional<Integer> perPage) {
    int pageCount = 0, perPageVal = 30;
    if (page.isPresent()) {
      pageCount = page.get();
    }
    if (perPage.isPresent()) {
      perPageVal = perPage.get();
    }

    Page<Flybase> flybasePage = flybaseRepo.findAll(new PageRequest(pageCount, perPageVal));
    if (!flybasePage.hasContent()) {
      return new JsonResult().noValue();
    }

    //return flybasePage;
    return gson.toJson(ImmutableList.copyOf(flybasePage));

  }

  @RequestMapping(value = "/{flybase_key}", method = RequestMethod.GET)
  public String getFlybaseById(@PathVariable("flybase_key") String flybaseKey,
                               @RequestParam(value = "page") Optional<Integer> page,
                               @RequestParam(value = "per_page") Optional<Integer> perPage)
      throws RequestParamException {

    int pageCount = 0, perPageVal = 30;
    if (page.isPresent()) {
      pageCount = page.get();
    }
    if (perPage.isPresent()) {
      perPageVal = perPage.get();
    }

    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }

    Page<Fly> flyPage =
        flyRepo.findByFlybaseId(new ObjectId(flybaseKey), new PageRequest(pageCount, perPageVal));

    if (!flyPage.hasContent()) {
      return new JsonResult().noValue();
    }
    return gson.toJson(ImmutableList.copyOf(flyPage));

  }

  @RequestMapping(value = "/{flybase_key}/stat", method = RequestMethod.GET)
  public String getFlybaseStatById(@PathVariable("flybase_key") String flybaseKey) throws RequestParamException {
    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }
    // TODO should change pageRequestThing
    Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseKey), new PageRequest(0, 200));

    return rawStatToJson(analyzer.analyzeStat(flies));
  }

  @RequestMapping(value = "/{flybase_key}/query", method = RequestMethod.GET)
  public String getFlybaseQueryById(@PathVariable("flybase_key") String flybaseKey) throws RequestParamException {

    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }
    Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseKey), new PageRequest(0, 200));

    return gson.toJson(analyzer.getQueryInfo(flies));
  }

  @RequestMapping(value = "{flybase_key}/keyword", method = RequestMethod.GET)
  public String getFlybaseKeywordsById(@PathVariable("flybase_key") String flybaseKey) throws RequestParamException {
    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }
    Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseKey), new PageRequest(0, 200));
    Map<QaPair, Integer> queryInfo = analyzer.getQueryInfo(flies);
    String addedString = "";
    for (QaPair e : queryInfo.keySet()) {
      addedString += e.getQuestion() + " ";
    }
    return gson.toJson(analyzer.assumeKeywords(addedString));
  }

  @RequestMapping(method = RequestMethod.POST)
  public String createFlybase(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("keywords") Optional<String> keywords) {

    Flybase.Builder builder = new Flybase.Builder(name)
        .description(description)
        .createTime(ZonedDateTime.now());

    if (keywords.isPresent()) {
      Type listType = new TypeToken<List<String>>() {
      }.getType();
      List<String> keywordList =
          gson.getGson().fromJson(keywords.get(), listType);
        /*
         * for (String s : keywords) { System.out.println("show keywords :" + s); }
         */
      builder.keywords(keywordList);
    }

    Flybase flybase = builder.build();
    Flybase resultFlybase = flybaseRepo.save(flybase);
    return gson.toJson(resultFlybase);
  }

  @RequestMapping(value = "/{flybase_key}", method = RequestMethod.POST)
  public String submitFlybaseRaw(@PathVariable("flybase_key") String flybaseKey,
                                 @RequestHeader("Content-Type") String contentType,
                                 @RequestBody String body)
      throws RequestParamException {
    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }

    if (contentType
        .contains(CustomMediaType.APPLICATION_CSV_VALUE)) {
      String csvInput = body;
      CSVtoFliesConverter converter = new CSVtoFliesConverter();
      List<Fly> flies = converter.parse(csvInput, new ObjectId(flybaseKey));
      List<Fly> resultFlies = flyRepo.save(flies);
      return gson.toJson(resultFlies);

    } else if (contentType
        .contains(CustomMediaType.APPLICATION_JSON_VALUE)) {
      String jsonInput = body;
      Type flyListType = new TypeToken<List<Fly>>() {
      }.getType();
      List<Fly> rawFlies = gson.getGson().fromJson(jsonInput, flyListType);
      List<Fly> flies = new ArrayList<Fly>();
      for (Fly e : rawFlies) {
        flies.add(new Fly.Builder(e.getQaPairs()).flybaseId(new ObjectId(flybaseKey))
            .createTime(ZonedDateTime.now()).build());
      }
      List<Fly> resultFlies = flyRepo.save(flies);
      return gson.toJson(resultFlies);

    } else {
      throw new RequestParamException(defaultMsg("error.notsupportedcontenttype"));
    }
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
