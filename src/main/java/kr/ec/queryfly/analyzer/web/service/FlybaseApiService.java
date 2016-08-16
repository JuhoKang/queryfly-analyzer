package kr.ec.queryfly.analyzer.web.service;

import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.PREFIX_GET;
import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.PREFIX_POST;
import static kr.ec.queryfly.analyzer.core.ApiRequestHandler.REQUEST_URI;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import kr.ec.queryfly.analyzer.core.SimpleCrudApiService;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.util.GsonUtil;
import kr.ec.queryfly.analyzer.util.JsonResult;

/**
 * needs further refactoring
 * @author Juho Kang
 *
 */
@Service("flybaseApiService")
public class FlybaseApiService extends SimpleCrudApiService {

  @Autowired
  FlybaseRepository flybaseRepo;

  @Autowired
  FlyRepository flyRepo;

  @Autowired
  GsonUtil gson;

  @Override
  public String whenGet(Map<String, String> request) throws RequestParamException {
    List<String> uriElements = getSplittedPath(request.get(REQUEST_URI));
    for (Map.Entry<String, String> entry : request.entrySet()) {
      System.out.println(entry.getKey() + "/" + entry.getValue());
    }

    //default value
    int page = 0;
    int perPage = 30;
    
    if (request.containsKey(PREFIX_GET + "page")) {
      // pagination is zero-based.
      page = Integer.parseInt(request.get(PREFIX_GET + "page")) - 1;
    }
    if (request.containsKey(PREFIX_GET + "per_page")) {
      perPage = Integer.parseInt(request.get(PREFIX_GET + "per_page"));
    }

    for (String s : uriElements) {
      System.out.println(s);
    }

    if (uriElements.size() == 1) {
      Page<Flybase> flybasePage = flybaseRepo.findAll(new PageRequest(page, perPage));
      if (!flybasePage.hasContent()) {
        return new JsonResult().noValue();
      }

      return gson.toJson(ImmutableList.copyOf(flybasePage));

    } else if (uriElements.size() == 2) {
      String id = uriElements.get(1);
      Page<Fly> flyPage =
          flyRepo.findByFlybaseId(new ObjectId(id), new PageRequest(page, perPage));
      if (!flyPage.hasContent()) {
        return new JsonResult().noValue();
      }

      return gson.toJson(ImmutableList.copyOf(flyPage));
    }

    List<Flybase> flybaseList = flybaseRepo.findAll();

    return gson.toJson(flybaseList);
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



}
