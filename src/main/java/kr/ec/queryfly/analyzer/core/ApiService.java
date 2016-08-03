package kr.ec.queryfly.analyzer.core;

import java.util.Map;

import kr.ec.queryfly.analyzer.web.service.RequestParamException;
import kr.ec.queryfly.analyzer.web.service.ServiceException;

/**
 * An ApiService returns a {@link String} respond matching to the request
 * 
 * @author Juho Kang
 *
 */
public interface ApiService {
  
  /**
   * 
   * @param request
   * @return
   * @throws ServiceException
   * @throws RequestParamException
   */

  public String serve(Map<String, String> request)
      throws ServiceException, RequestParamException;

}
