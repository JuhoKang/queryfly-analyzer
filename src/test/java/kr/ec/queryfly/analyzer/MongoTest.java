package kr.ec.queryfly.analyzer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.ec.queryfly.analyzer.config.AppServerContextConfig;
import kr.ec.queryfly.analyzer.config.MongoConfig;
import kr.ec.queryfly.analyzer.core.ApiRequestHandler;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.AnswerOption;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.model.QaPair;
import kr.ec.queryfly.analyzer.web.service.FlyApiService;
import kr.ec.queryfly.analyzer.web.service.FlybaseApiService;
import kr.ec.queryfly.analyzer.web.service.RequestParamException;
import kr.ec.queryfly.analyzer.web.service.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppServerContextConfig.class, MongoConfig.class})
public class MongoTest {

  @Autowired
  FlyRepository flyRepo;

  @Autowired
  FlybaseRepository flyBaseRepo;

  @Autowired
  FlyApiService flyApiService;

  @Autowired
  FlybaseApiService flybaseApiService;

  @Before
  public void setUp() {

  }

  @After
  public void tearDown() {

  }

  @Test
  public void testMongo() {

    // flyBaseRepo.deleteAll();
    // flyRepo.deleteAll();
    //
    // Flybase fb = new Flybase.Builder("flybase 1").description("description")
    // .createTime(ZonedDateTime.now()).build();
    // Flybase savedFb = flyBaseRepo.save(fb);
    //
    // Map<String, String> map = new HashMap<String, String>();
    // map.put(ApiRequestHandler.REQUEST_METHOD, "POST");
    // map.put(ApiRequestHandler.REQUEST_URI, "/fly");
    // map.put(ApiRequestHandler.PREFIX_POST + "flybase_key", savedFb.getId().toHexString());
    // String case1 = null;
    // try {
    // case1 = Files.toString(new File(this.getClass().getResource("/InputCase1").toURI()),
    // Charsets.UTF_8);
    // } catch (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    // String[] split = case1.split("&");
    // for (String s : split) {
    // map.put(ApiRequestHandler.PREFIX_POST + "content", s);
    // try {
    // flyApiService.serve(map);
    // } catch (ServiceException | RequestParamException e) { // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }


  }

}
