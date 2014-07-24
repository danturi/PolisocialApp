/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-09 17:08:39 UTC)
 * on 2014-07-24 at 13:13:33 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.secondhandbookendpoint;

/**
 * Service definition for Secondhandbookendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link SecondhandbookendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Secondhandbookendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the secondhandbookendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myapp.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "secondhandbookendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Secondhandbookendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Secondhandbookendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getSecondHandBook".
   *
   * This request holds the parameters needed by the secondhandbookendpoint server.  After setting any
   * optional parameters, call the {@link GetSecondHandBook#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetSecondHandBook getSecondHandBook(java.lang.Long id) throws java.io.IOException {
    GetSecondHandBook result = new GetSecondHandBook(id);
    initialize(result);
    return result;
  }

  public class GetSecondHandBook extends SecondhandbookendpointRequest<it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook> {

    private static final String REST_PATH = "secondhandbook/{id}";

    /**
     * Create a request for the method "getSecondHandBook".
     *
     * This request holds the parameters needed by the the secondhandbookendpoint server.  After
     * setting any optional parameters, call the {@link GetSecondHandBook#execute()} method to invoke
     * the remote operation. <p> {@link GetSecondHandBook#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetSecondHandBook(java.lang.Long id) {
      super(Secondhandbookendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetSecondHandBook setAlt(java.lang.String alt) {
      return (GetSecondHandBook) super.setAlt(alt);
    }

    @Override
    public GetSecondHandBook setFields(java.lang.String fields) {
      return (GetSecondHandBook) super.setFields(fields);
    }

    @Override
    public GetSecondHandBook setKey(java.lang.String key) {
      return (GetSecondHandBook) super.setKey(key);
    }

    @Override
    public GetSecondHandBook setOauthToken(java.lang.String oauthToken) {
      return (GetSecondHandBook) super.setOauthToken(oauthToken);
    }

    @Override
    public GetSecondHandBook setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetSecondHandBook) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetSecondHandBook setQuotaUser(java.lang.String quotaUser) {
      return (GetSecondHandBook) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetSecondHandBook setUserIp(java.lang.String userIp) {
      return (GetSecondHandBook) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetSecondHandBook setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetSecondHandBook set(String parameterName, Object value) {
      return (GetSecondHandBook) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertSecondHandBook".
   *
   * This request holds the parameters needed by the secondhandbookendpoint server.  After setting any
   * optional parameters, call the {@link InsertSecondHandBook#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook}
   * @return the request
   */
  public InsertSecondHandBook insertSecondHandBook(it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook content) throws java.io.IOException {
    InsertSecondHandBook result = new InsertSecondHandBook(content);
    initialize(result);
    return result;
  }

  public class InsertSecondHandBook extends SecondhandbookendpointRequest<it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook> {

    private static final String REST_PATH = "secondhandbook";

    /**
     * Create a request for the method "insertSecondHandBook".
     *
     * This request holds the parameters needed by the the secondhandbookendpoint server.  After
     * setting any optional parameters, call the {@link InsertSecondHandBook#execute()} method to
     * invoke the remote operation. <p> {@link InsertSecondHandBook#initialize(com.google.api.client.g
     * oogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook}
     * @since 1.13
     */
    protected InsertSecondHandBook(it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook content) {
      super(Secondhandbookendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook.class);
    }

    @Override
    public InsertSecondHandBook setAlt(java.lang.String alt) {
      return (InsertSecondHandBook) super.setAlt(alt);
    }

    @Override
    public InsertSecondHandBook setFields(java.lang.String fields) {
      return (InsertSecondHandBook) super.setFields(fields);
    }

    @Override
    public InsertSecondHandBook setKey(java.lang.String key) {
      return (InsertSecondHandBook) super.setKey(key);
    }

    @Override
    public InsertSecondHandBook setOauthToken(java.lang.String oauthToken) {
      return (InsertSecondHandBook) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertSecondHandBook setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertSecondHandBook) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertSecondHandBook setQuotaUser(java.lang.String quotaUser) {
      return (InsertSecondHandBook) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertSecondHandBook setUserIp(java.lang.String userIp) {
      return (InsertSecondHandBook) super.setUserIp(userIp);
    }

    @Override
    public InsertSecondHandBook set(String parameterName, Object value) {
      return (InsertSecondHandBook) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listSecondHandBook".
   *
   * This request holds the parameters needed by the secondhandbookendpoint server.  After setting any
   * optional parameters, call the {@link ListSecondHandBook#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListSecondHandBook listSecondHandBook() throws java.io.IOException {
    ListSecondHandBook result = new ListSecondHandBook();
    initialize(result);
    return result;
  }

  public class ListSecondHandBook extends SecondhandbookendpointRequest<it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.CollectionResponseSecondHandBook> {

    private static final String REST_PATH = "secondhandbook";

    /**
     * Create a request for the method "listSecondHandBook".
     *
     * This request holds the parameters needed by the the secondhandbookendpoint server.  After
     * setting any optional parameters, call the {@link ListSecondHandBook#execute()} method to invoke
     * the remote operation. <p> {@link ListSecondHandBook#initialize(com.google.api.client.googleapis
     * .services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListSecondHandBook() {
      super(Secondhandbookendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.CollectionResponseSecondHandBook.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListSecondHandBook setAlt(java.lang.String alt) {
      return (ListSecondHandBook) super.setAlt(alt);
    }

    @Override
    public ListSecondHandBook setFields(java.lang.String fields) {
      return (ListSecondHandBook) super.setFields(fields);
    }

    @Override
    public ListSecondHandBook setKey(java.lang.String key) {
      return (ListSecondHandBook) super.setKey(key);
    }

    @Override
    public ListSecondHandBook setOauthToken(java.lang.String oauthToken) {
      return (ListSecondHandBook) super.setOauthToken(oauthToken);
    }

    @Override
    public ListSecondHandBook setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListSecondHandBook) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListSecondHandBook setQuotaUser(java.lang.String quotaUser) {
      return (ListSecondHandBook) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListSecondHandBook setUserIp(java.lang.String userIp) {
      return (ListSecondHandBook) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListSecondHandBook setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListSecondHandBook setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListSecondHandBook set(String parameterName, Object value) {
      return (ListSecondHandBook) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeSecondHandBook".
   *
   * This request holds the parameters needed by the secondhandbookendpoint server.  After setting any
   * optional parameters, call the {@link RemoveSecondHandBook#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveSecondHandBook removeSecondHandBook(java.lang.Long id) throws java.io.IOException {
    RemoveSecondHandBook result = new RemoveSecondHandBook(id);
    initialize(result);
    return result;
  }

  public class RemoveSecondHandBook extends SecondhandbookendpointRequest<Void> {

    private static final String REST_PATH = "secondhandbook/{id}";

    /**
     * Create a request for the method "removeSecondHandBook".
     *
     * This request holds the parameters needed by the the secondhandbookendpoint server.  After
     * setting any optional parameters, call the {@link RemoveSecondHandBook#execute()} method to
     * invoke the remote operation. <p> {@link RemoveSecondHandBook#initialize(com.google.api.client.g
     * oogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveSecondHandBook(java.lang.Long id) {
      super(Secondhandbookendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveSecondHandBook setAlt(java.lang.String alt) {
      return (RemoveSecondHandBook) super.setAlt(alt);
    }

    @Override
    public RemoveSecondHandBook setFields(java.lang.String fields) {
      return (RemoveSecondHandBook) super.setFields(fields);
    }

    @Override
    public RemoveSecondHandBook setKey(java.lang.String key) {
      return (RemoveSecondHandBook) super.setKey(key);
    }

    @Override
    public RemoveSecondHandBook setOauthToken(java.lang.String oauthToken) {
      return (RemoveSecondHandBook) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveSecondHandBook setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveSecondHandBook) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveSecondHandBook setQuotaUser(java.lang.String quotaUser) {
      return (RemoveSecondHandBook) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveSecondHandBook setUserIp(java.lang.String userIp) {
      return (RemoveSecondHandBook) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveSecondHandBook setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveSecondHandBook set(String parameterName, Object value) {
      return (RemoveSecondHandBook) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateSecondHandBook".
   *
   * This request holds the parameters needed by the secondhandbookendpoint server.  After setting any
   * optional parameters, call the {@link UpdateSecondHandBook#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook}
   * @return the request
   */
  public UpdateSecondHandBook updateSecondHandBook(it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook content) throws java.io.IOException {
    UpdateSecondHandBook result = new UpdateSecondHandBook(content);
    initialize(result);
    return result;
  }

  public class UpdateSecondHandBook extends SecondhandbookendpointRequest<it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook> {

    private static final String REST_PATH = "secondhandbook";

    /**
     * Create a request for the method "updateSecondHandBook".
     *
     * This request holds the parameters needed by the the secondhandbookendpoint server.  After
     * setting any optional parameters, call the {@link UpdateSecondHandBook#execute()} method to
     * invoke the remote operation. <p> {@link UpdateSecondHandBook#initialize(com.google.api.client.g
     * oogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook}
     * @since 1.13
     */
    protected UpdateSecondHandBook(it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook content) {
      super(Secondhandbookendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook.class);
    }

    @Override
    public UpdateSecondHandBook setAlt(java.lang.String alt) {
      return (UpdateSecondHandBook) super.setAlt(alt);
    }

    @Override
    public UpdateSecondHandBook setFields(java.lang.String fields) {
      return (UpdateSecondHandBook) super.setFields(fields);
    }

    @Override
    public UpdateSecondHandBook setKey(java.lang.String key) {
      return (UpdateSecondHandBook) super.setKey(key);
    }

    @Override
    public UpdateSecondHandBook setOauthToken(java.lang.String oauthToken) {
      return (UpdateSecondHandBook) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateSecondHandBook setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateSecondHandBook) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateSecondHandBook setQuotaUser(java.lang.String quotaUser) {
      return (UpdateSecondHandBook) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateSecondHandBook setUserIp(java.lang.String userIp) {
      return (UpdateSecondHandBook) super.setUserIp(userIp);
    }

    @Override
    public UpdateSecondHandBook set(String parameterName, Object value) {
      return (UpdateSecondHandBook) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Secondhandbookendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Secondhandbookendpoint}. */
    @Override
    public Secondhandbookendpoint build() {
      return new Secondhandbookendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link SecondhandbookendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setSecondhandbookendpointRequestInitializer(
        SecondhandbookendpointRequestInitializer secondhandbookendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(secondhandbookendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
