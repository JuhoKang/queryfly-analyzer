package kr.ec.queryfly.analyzer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "kr.ec.queryfly.analyzer.data.service")
public class MongoConfig extends AbstractMongoConfiguration {


  private final ApplicationContext springContext;

  @Autowired
  public MongoConfig(ApplicationContext springContext) {
    this.springContext = springContext;
  }

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
    List<Object> conversions = new ArrayList<Object>(
        springContext.getBeansWithAnnotation(WritingConverter.class).values());
    conversions.addAll(springContext.getBeansWithAnnotation(ReadingConverter.class).values());
    return new CustomConversions(conversions);
  }

}
