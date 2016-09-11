package kr.ec.queryfly.analyzer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import kr.ec.queryfly.analyzer.config.AppServerContextConfig;
import kr.ec.queryfly.analyzer.config.MongoConfig;
import kr.ec.queryfly.analyzer.core.ServiceDispatcher;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.ApiRequest;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.stat.CSVtoFliesConverter;
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

    // if you want to clean up.
    // flyBaseRepo.deleteAll();
    // flyRepo.deleteAll();

  }

  @Test
  public void jsonInputSetUp() {
    Flybase fb = new Flybase.Builder("flybase json").description("description")
        .createTime(ZonedDateTime.now()).build();
    Flybase jsonFb = flyBaseRepo.save(fb);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    String jsonCase = null;
    try {
      jsonCase = Files.toString(new File(this.getClass().getResource("/InputCase1").toURI()),
          Charsets.UTF_8);
    } catch (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    ApiRequest request = new ApiRequest.Builder("/flybase/" + jsonFb.getId().toHexString(), "POST")
        .headers(headers).postRawData(jsonCase).build();

    try {
      flybaseApiService.serve(request);
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RequestParamException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void csvInputSetUp() {
    Flybase fb2 = new Flybase.Builder("flybase csv").description("description")
        .createTime(ZonedDateTime.now()).build();
    Flybase csvFb = flyBaseRepo.save(fb2);
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/csv");

    String csvCase = null;
    try {
      csvCase = Files.toString(new File(this.getClass().getResource("/testform.csv").toURI()),
          Charsets.UTF_8);
    } catch (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    ApiRequest request = new ApiRequest.Builder("/flybase/" + csvFb.getId().toHexString(), "POST")
        .headers(headers).postRawData(csvCase).build();

    try {
      flybaseApiService.serve(request);
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RequestParamException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }

}
