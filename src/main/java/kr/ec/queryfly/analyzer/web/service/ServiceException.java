package kr.ec.queryfly.analyzer.web.service;

public class ServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 2871567456263704751L;

  public ServiceException() {
    super();
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }

  protected ServiceException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
