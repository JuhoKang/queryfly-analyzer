package kr.ec.queryfly.analyzer.model;

import org.springframework.http.MediaType;

/**
 * <p>
 * only adding constant Strings to MediaType. Not using the features of MediaType
 * </p>
 * 
 * @author Juho Kang
 *
 */
public class CustomMediaType extends MediaType {

  public static final String HEADER_CONTENT_TYPE_VALUE = "Content-Type";

  public static final String APPLICATION_CSV_VALUE = "application/csv";


  /**
   * 
   */
  private static final long serialVersionUID = 4032945354748537914L;

  public CustomMediaType(String type) {
    super(type);
  }

}
