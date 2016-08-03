package kr.ec.queryfly.analyzer;

import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import kr.ec.queryfly.analyzer.config.AppServerContextConfig;
import kr.ec.queryfly.analyzer.config.MongoConfig;
import kr.ec.queryfly.analyzer.data.util.AnswerOptionReadConverter;
import kr.ec.queryfly.analyzer.data.util.AnswerOptionWriteConverter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {AppServerContextConfig.class, MongoConfig.class})
public class AnswerOptionConverterTest {

  AnswerOptionReadConverter rc = new AnswerOptionReadConverter();

  AnswerOptionWriteConverter wc = new AnswerOptionWriteConverter();


  @Before
  public void setUp() {

  }

  @After
  public void tearDown() {

  }

  @Test
  public void testConvert() {
    //assertEquals();

  }

}
