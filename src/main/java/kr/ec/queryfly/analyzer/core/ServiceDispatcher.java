package kr.ec.queryfly.analyzer.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceDispatcher {

  private static ApplicationContext springContext;

  @Autowired
  public void init(ApplicationContext springContext) {
    ServiceDispatcher.springContext = springContext;
  }

  protected static Logger logger = LoggerFactory.getLogger(ServiceDispatcher.class);

  public static ApiService dispatch(Map<String, String> requestMap) {
    String serviceUri = requestMap.get(ApiRequestHandler.REQUEST_URI);
    String beanName = null;
    if (serviceUri == null) {
      beanName = "notFound";
    } else {
      String serviceEntry = serviceUri.split("/")[1];
      logger.info("serviceEntry : " + serviceEntry);
      beanName = findService(serviceEntry);
    }

    ApiService service = null;
    try {
      service = (ApiService) springContext.getBean(beanName, requestMap);
    } catch (Exception e) {
      e.printStackTrace();
      service = (ApiService) springContext.getBean("notFound", requestMap);
    }

    return service;
  }

  private static String findService(String entry) {
    if (entry.equals("flybase")) {
      return "flybaseService";
    } else if (entry.equals("fly")) {
      return "flyService";
    } else {
      return "notFound";
    }
  }
}
