package kr.ec.queryfly.analyzer.web.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.util.GsonUtil;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;

/**
 * Created by juho on 16. 12. 20.
 */

@RestController
@RequestMapping("/fly")
public class FlyController {

  private final FlyRepository flyRepo;

  private final GsonUtil gson;

  private final MessageSource msg;

  @Autowired
  public FlyController(FlyRepository flyRepo, GsonUtil gson, MessageSource msg) {
    this.flyRepo = flyRepo;
    this.gson = gson;
    this.msg = msg;
  }


  @RequestMapping(method = RequestMethod.POST)
  public String postFly(@RequestHeader("Content-Type") String contentType,
                        @RequestHeader("flybase_key") String flybaseKey,
                        @RequestBody String contentJson)
      throws RequestParamException {

    if (!ObjectId.isValid(flybaseKey)) {
      throw new RequestParamException(defaultMsg("error.notvalidfbkey"));
    }

    ObjectId flybaseId = new ObjectId(flybaseKey);

    Fly contentFly = gson.getGson().fromJson(contentJson, Fly.class);

    Fly fly = new Fly.Builder(contentFly.getQaPairs()).flybaseId(flybaseId).build();

    Fly resultFly = flyRepo.save(fly);

    return gson.toJson(resultFly);

  }

  @RequestMapping(method = RequestMethod.GET)
  public String testFly(){
    return "hi";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String getFlyRaw(@PathVariable String id) {
    Fly resultFly = flyRepo.findOne(new ObjectId(id));
    return gson.toJson(resultFly);
  }

  @RequestMapping(value = "/{id}/stat", method = RequestMethod.GET)
  public String getFlyStat(@PathVariable String id) throws RequestParamException {

    // TODO
    // do the stat thing.
    return null;

  }


  private String defaultMsg(String key) {
    return msg.getMessage(key, null, Locale.KOREA);
  }

  private String defaultMsgArg(String key, String arg) {
    return msg.getMessage(key + arg, null, Locale.KOREA);
  }

}
