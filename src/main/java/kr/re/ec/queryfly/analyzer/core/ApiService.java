package kr.re.ec.queryfly.analyzer.core;

import java.util.Map;

import kr.re.ec.queryfly.analyzer.service.ServiceException;

/**
 * An ApiService returns a {@link String} respond matching to the request
 * @author Juho Kang
 *
 */
public interface ApiService {
  
  /**
   * 
   * @param request
   * @return
   */
  public String serve(Map<String,String> request) throws ServiceException;
  
}
