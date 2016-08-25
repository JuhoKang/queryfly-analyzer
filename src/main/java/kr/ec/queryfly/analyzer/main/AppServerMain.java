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
      springContext = new AnnotationConfigApplicationContext(AppServerContextConfig.class);
      springContext.registerShutdownHook();

      AppServerBootstrapper serverBootstrapper = springContext.getBean(AppServerBootstrapper.class);

      serverBootstrapper.start();
    } finally {
      springContext.close();
    }

  }
}
