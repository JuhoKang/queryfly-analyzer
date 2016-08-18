package kr.ec.queryfly.analyzer.web.service;

import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.PREFIX_GET;
import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.PREFIX_POST;
import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.REQUEST_URI;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
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

  @Autowired
  public FlybaseApiService(FlybaseRepository flybaseRepo, FlyRepository flyRepo, GsonUtil gson,
      FlybaseAnalyzer analyzer) {
    this.flybaseRepo = flybaseRepo;
    this.flyRepo = flyRepo;
    this.gson = gson;
    this.analyzer = analyzer;
  }

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    List<String> uriElements = getSplittedPath(request.get(REQUEST_URI));
    /*
     * for (Map.Entry<String, String> entry : request.entrySet()) {
     * System.out.println(entry.getKey() + "/" + entry.getValue()); }
     */

    // default value
    int page = 0;
    int perPage = 30;

    if (request.containsKey(PREFIX_GET + "page")) {
      // pagination is zero-based.
      page = Integer.parseInt(request.get(PREFIX_GET + "page")) - 1;
    }
    if (request.containsKey(PREFIX_GET + "per_page")) {
      perPage = Integer.parseInt(request.get(PREFIX_GET + "per_page"));
    }

    // ("/flybase")
    if (uriElements.size() == 1) {
      System.out.println("user in");
      Page<Flybase> flybasePage = flybaseRepo.findAll(new PageRequest(page, perPage));
      if (!flybasePage.hasContent()) {
        return new JsonResult().noValue();
      }

      return gson.toJson(ImmutableList.copyOf(flybasePage));

      // ("/flybase/{id}")
    } else if (uriElements.size() == 2) {
      String flybaseId = uriElements.get(1);
      if (!ObjectId.isValid(flybaseId)) {
        throw new RequestParamException("not a valid flybase_key.");
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
        throw new RequestParamException("not a valid flybase_key.");
      }

      if (operation.equals("stat")) {

        Page<Fly> flies = flyRepo.findByFlybaseId(new ObjectId(flybaseId), new PageRequest(0, 200));

        return rawStatToJson(analyzer.analyze(flies));

      } else if (operation.equals("query")) {

      } else {
        throw new RequestParamException("no such operation for flybase.");
      }
    } else {
      throw new RequestParamException("not a valid api request for flybase.");
    }
    return new JsonResult().noValue();
  }

  @Override
  public String whenPost(Map<String, String> request) throws RequestParamException {

    if (!request.containsKey(PREFIX_POST + "name")
        || !request.containsKey(PREFIX_POST + "description")) {
      throw new RequestParamException();
    }

    Flybase flybase = new Flybase.Builder(request.get(PREFIX_POST + "name"))
        .description(request.get(PREFIX_POST + "description")).createTime(ZonedDateTime.now())
        .build();
    Flybase resultFlybase = flybaseRepo.save(flybase);
    return gson.toJson(resultFlybase);
  }

  @Override
  public String whenPut(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String whenDelete(Map<String, String> request) throws RequestParamException {
    // TODO Auto-generated method stub
    return null;
  }


  private String rawStatToJson(Map<String, String> rawStat) {
    JsonObject obj = new JsonObject();
    JsonArray array = new JsonArray();
    Map<String, List<String>> temp = new HashMap<String, List<String>>();
    for (Map.Entry<String, String> entry : rawStat.entrySet()) {
      List<String> qa = Splitter.on(FlybaseAnalyzer.Q_A_SEPERATOR).splitToList(entry.getKey());
      String q = qa.get(0);
      String a = qa.get(1);
      if (!temp.containsKey(q)) {
        List<String> templist = new ArrayList<String>();
        templist.add(a + FlybaseAnalyzer.Q_A_SEPERATOR + entry.getValue());
        temp.put(q, templist);
      } else {
        List<String> templist = temp.get(q);
        templist.add(a + FlybaseAnalyzer.Q_A_SEPERATOR + entry.getValue());
        temp.put(q, templist);
      }
    }

    for (Map.Entry<String, List<String>> entry : temp.entrySet()) {
      JsonObject tempObj = new JsonObject();
      tempObj.addProperty("question", entry.getKey());
      JsonArray tempArray = new JsonArray();
      for (String ac : entry.getValue()) {
        JsonObject answerCount = new JsonObject();
        List<String> splitted = Splitter.on(FlybaseAnalyzer.Q_A_SEPERATOR).splitToList(ac);
        answerCount.addProperty("answer", splitted.get(0));
        answerCount.addProperty("count", splitted.get(1));
        tempArray.add(answerCount);
      }
      tempObj.add("answerCounts", tempArray);
      array.add(tempObj);
    }
    obj.add("qaStats", array);

    return obj.toString();
  }


}
