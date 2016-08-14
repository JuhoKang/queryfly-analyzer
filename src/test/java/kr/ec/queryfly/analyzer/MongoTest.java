package kr.ec.queryfly.analyzer;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.ec.queryfly.analyzer.config.AppServerContextConfig;
import kr.ec.queryfly.analyzer.config.MongoConfig;
import kr.ec.queryfly.analyzer.data.service.FlyRepository;
import kr.ec.queryfly.analyzer.data.service.FlybaseRepository;
import kr.ec.queryfly.analyzer.model.AnswerOption;
import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.Flybase;
import kr.ec.queryfly.analyzer.model.QaPair;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {AppServerContextConfig.class, MongoConfig.class})
public class MongoTest {

  @Autowired
  FlyRepository flyRepo;
  
  @Autowired
  FlybaseRepository flyBaseRepo;

  @Before
  public void setUp() {

  }

  @After
  public void tearDown() {

  }

  @Test
  public void testMongo() {
    flyBaseRepo.deleteAll();
    flyRepo.deleteAll();
    
    Flybase fb = new Flybase.Builder("flybase 1").description("description").createTime(ZonedDateTime.now()).build();
    flyBaseRepo.save(fb);
    Flybase savedFb = flyBaseRepo.findAll().get(0);
    
    List<String> ap1 = new ArrayList<String>();
    ap1.add("1");

    ap1.add("2");

    ap1.add("3");

    ap1.add("4");

    ap1.add("5");

    AnswerOption ao1 =
        new AnswerOption.Builder("select-5").answerPool(ap1).build();
    QaPair pair1 =
        new QaPair.Builder("question 1").answer("1").answerOption(ao1).build();

    QaPair pair2 =
        new QaPair.Builder("question 2").answer("2").answerOption(ao1).build();
    List<QaPair> qaPairs = new ArrayList<QaPair>();
    qaPairs.add(pair1);
    qaPairs.add(pair2);
    Fly fly = new Fly.Builder(savedFb.getId(),qaPairs).build();

    System.out.println("count before save: " + flyRepo.count());
    flyRepo.save(fly);
    System.out.println("count after save: " + flyRepo.count());

    List<Fly> flies = flyRepo.findAll();

    
    for (Fly f : flies) {
      System.out.println(f);
    }

    // Fly searchfly = flyRepo.findOne(new ObjectId("57a1e99ce6d6301ea0c71dc5"));


    // System.out.println("searched this "+searchfly);

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // String jsonOutput = gson.toJson(searchfly);

    System.out.println("flybase is");
    System.out.println(gson.toJson(savedFb));
    
    System.out.println("json output of this ");
    // System.out.println(jsonOutput);

    String jsonOutputList = gson.toJson(flies);
    System.out.println(jsonOutputList);
  }

}
