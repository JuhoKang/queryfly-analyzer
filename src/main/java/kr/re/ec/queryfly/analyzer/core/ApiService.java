package kr.re.ec.queryfly.analyzer.core;

import java.util.Map;

import kr.re.ec.queryfly.analyzer.service.RequestParamException;
import kr.re.ec.queryfly.analyzer.service.ServiceException;

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
