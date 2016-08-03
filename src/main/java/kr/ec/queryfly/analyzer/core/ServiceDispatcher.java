package kr.ec.queryfly.analyzer.core;

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

  protected static Logger logger =
      LoggerFactory.getLogger(ServiceDispatcher.class);

  public static ApiService dispatch(Map<String, String> requestMap) {
    String serviceUri = requestMap.get("REQUEST_URI");
    String beanName = null;

    if (serviceUri == null) {
      beanName = "notFound";
    }

    if (serviceUri.startsWith("/tokens")) {
      String httpMethod = requestMap.get("REQUEST_METHOD");

      switch (httpMethod) {
        case "POST":
          beanName = "tokenIssue";
          break;
        case "DELETE":
          beanName = "tokenExpier";
          break;
        case "GET":
          beanName = "tokenVerify";
          break;

        default:
          beanName = "notFound";
          break;
      }
    } else if (serviceUri.startsWith("/users")) {
      beanName = "users";
    } else if (serviceUri.startsWith("/test")) {
      beanName = "test";
    } else if (serviceUri.startsWith("/generateId")) {
      beanName = "idGenerator";
    } else {
      beanName = "notFound";
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
}
