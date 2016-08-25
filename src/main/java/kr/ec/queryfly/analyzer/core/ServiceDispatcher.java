package kr.ec.queryfly.analyzer.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

import io.netty.handler.codec.http.QueryStringDecoder;
import kr.ec.queryfly.analyzer.model.ApiRequest;

@Component
public class ServiceDispatcher {

  private static ApplicationContext springContext;

  @Autowired
  public void init(ApplicationContext springContext) {
    ServiceDispatcher.springContext = springContext;
  }

  protected static Logger logger = LoggerFactory.getLogger(ServiceDispatcher.class);

  public static ApiService dispatch(ApiRequest apiReq) {
    String serviceUri = apiReq.getUri();
    String beanName = null;
    if (serviceUri == null) {
      beanName = "notFound";
    } else {
      String serviceEntry = getSplittedPath(serviceUri).get(0);
      logger.info("serviceEntry : " + serviceEntry);
      beanName = findService(serviceEntry);
    }

    ApiService service = null;
    try {
      service = (ApiService) springContext.getBean(beanName, apiReq);
    } catch (Exception e) {
      e.printStackTrace();
      service = (ApiService) springContext.getBean("notFound", apiReq);
    }

    return service;
  }

  private static String findService(String entry) {
    if (entry.equals("flybase")) {
      return "flybaseApiService";
    } else if (entry.equals("fly")) {
      return "flyApiService";
    } else {
      return "notFound";
    }
  }

  private static List<String> getSplittedPath(String requestUri) {
    // get rid of the first "/"
    String uri = new QueryStringDecoder(requestUri).path().substring(1);
    List<String> result = Splitter.onPattern("/").splitToList(uri);

    // uriElements[0] is fly.
    return result;
  }
}
