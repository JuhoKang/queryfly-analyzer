package kr.ec.queryfly.analyzer.model;

import java.util.List;
import java.util.Map;

public class ApiRequest {

  private final String uri;
  private final String method;
  private final Map<String, String> headers;
  // querystring get parameters
  private final Map<String, List<String>> parameters;
  private final Map<String, String> postFormData;
  private final String postRawData;

  public String getUri() {
    return uri;
  }

  public String getMethod() {
    return method;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Map<String, List<String>> getParameters() {
    return parameters;
  }

  public Map<String, String> getPostFormData() {
    return postFormData;
  }

  public String getPostRawData() {
    return postRawData;
  }

  private ApiRequest(Builder builder) {
    this.uri = builder.uri;
    this.method = builder.method;
    this.headers = builder.headers;
    this.parameters = builder.parameters;
    this.postFormData = builder.postFormData;
    this.postRawData = builder.postRawData;
  }

  public static class Builder {
    private final String uri;
    private final String method;
    private Map<String, String> headers;
    private Map<String, List<String>> parameters;
    private Map<String, String> postFormData;
    private String postRawData;

    public static Builder from(ApiRequest source) {
      Builder builder = new Builder(source.getUri(), source.getMethod());
      if (source.getHeaders() != null) {
        builder = builder.headers(source.getHeaders());
      }
      if (source.getParameters() != null) {
        builder = builder.parameters(source.getParameters());
      }
      if (source.getPostFormData() != null) {
        builder = builder.postFormData(source.getPostFormData());
      }
      if (source.getPostRawData() != null) {
        builder = builder.postRawData(source.getPostRawData());
      }
      return builder;
    }

    public Builder(String uri, String method) {
      this.uri = uri;
      this.method = method;
    }

    public Builder headers(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder parameters(Map<String, List<String>> parameters) {
      this.parameters = parameters;
      return this;
    }

    public Builder postFormData(Map<String, String> postFormData) {
      this.postFormData = postFormData;
      return this;
    }

    public Builder postRawData(String postRawData) {
      this.postRawData = postRawData;
      return this;
    }

    public ApiRequest build() {
      return new ApiRequest(this);
    }

  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("ApiRequest [uri=");
    builder2.append(uri);
    builder2.append(", method=");
    builder2.append(method);
    builder2.append(", headers=");
    builder2.append(headers);
    builder2.append(", parameters=");
    builder2.append(parameters);
    builder2.append(", postFormData=");
    builder2.append(postFormData);
    builder2.append(", postRawData=");
    builder2.append(postRawData);
    builder2.append("]");
    return builder2.toString();
  }

}
