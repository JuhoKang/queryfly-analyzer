package kr.ec.queryfly.analyzer.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import kr.ec.queryfly.analyzer.config.AppServerBootstrapper;
import kr.ec.queryfly.analyzer.config.AppServerContextConfig;

/**
 * Main
 *
 */
public class AppServerMain {
  public static void main(String[] args) {

    AbstractApplicationContext springContext = null;
    try {
      springContext =
          new AnnotationConfigApplicationContext(AppServerContextConfig.class);
      springContext.registerShutdownHook();

      AppServerBootstrapper serverBootstrapper =
          springContext.getBean(AppServerBootstrapper.class);

      serverBootstrapper.start();
    } finally {
      springContext.close();
    }


    // MongoClient mongoClient = new MongoClient();
    // MongoDatabase db = mongoClient.getDatabase("queryfly");
    // mongoClient.
    // db.getCollection("restaurants")
    // .insertOne(
    // new Document("address",
    // new Document().append("street", "2 Avenue")
    // .append("zipcode", "10075").append("building", "1480")
    // .append("coord", asList(-73.9557413, 40.7720266)))
    // .append("borough",
    // "Manhattan")
    // .append("cuisine",
    // "Italian")
    // .append("grades",
    // asList(
    // new Document()
    // .append("date",
    // format.parse("2014-10-01T00:00:00Z"))
    // .append("grade", "A").append("score",
    // 11),
    // new Document()
    // .append("date",
    // format.parse("2014-01-16T00:00:00Z"))
    // .append("grade", "B").append("score", 17)))
    // .append("name", "Vella")
    // .append("restaurant_id", "41704620"));
    //
    // FindIterable<Document> iterable = db.getCollection("restaurants").find();
    //
    // iterable.forEach(new Block<Document>() {
    //
    // @Override
    // public void apply(final Document document) {
    // System.out.println(document);
    // }
    // });
    //
    // System.out.println("Hello World!");

  }
}
