package kr.ec.queryfly.analyzer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ec.queryfly.analyzer.config.AppServerContextConfig;
import kr.ec.queryfly.analyzer.config.MongoConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {AppServerContextConfig.class, MongoConfig.class})
public class AnswerOptionConverterTest {

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
