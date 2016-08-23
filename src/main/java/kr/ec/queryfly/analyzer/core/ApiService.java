package kr.ec.queryfly.analyzer.core;

import kr.ec.queryfly.analyzer.model.ApiRequest;
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

  public String serve(ApiRequest request)
      throws ServiceException, RequestParamException;

}
