package kr.ec.queryfly.analyzer.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import kr.ec.queryfly.analyzer.core.ServiceDispatcher;
import kr.ec.queryfly.analyzer.data.util.AnswerOptionReadConverter;
import kr.ec.queryfly.analyzer.data.util.AnswerOptionWriteConverter;
import kr.ec.queryfly.analyzer.data.util.FlyReadConverter;
import kr.ec.queryfly.analyzer.data.util.FlyWriteConverter;
import kr.ec.queryfly.analyzer.data.util.QaPairReadConverter;
import kr.ec.queryfly.analyzer.data.util.QaPairWriteConverter;

@Configuration
@EnableMongoRepositories(basePackages = "kr.ec.queryfly.analyzer.data.service")
public class MongoConfig extends AbstractMongoConfiguration {

  @Autowired
  private ApplicationContext springContext;

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient("127.0.0.1", 27017);
  }

  @Override
  protected String getMappingBasePackage() {
    return "kr.ec.querfly.analyzer.model";
  }

  @Override
  public CustomConversions customConversions() {

    /*
     * String[] names = springContext.getBeanDefinitionNames(); for (int i = 0; i < names.length;
     * i++) { System.out.println(names[i]); } System.out.println("are the beans ready" +
     * springContext.containsBean("answerOptionReadConverter"));
     */
    springContext.getBean(AnswerOptionReadConverter.class);
    return new CustomConversions(
        Arrays.asList(springContext.getBean(AnswerOptionReadConverter.class),
            springContext.getBean(AnswerOptionWriteConverter.class),
            springContext.getBean(FlyReadConverter.class),
            springContext.getBean(FlyWriteConverter.class),
            springContext.getBean(QaPairReadConverter.class),
            springContext.getBean(QaPairWriteConverter.class)));
  }

}
