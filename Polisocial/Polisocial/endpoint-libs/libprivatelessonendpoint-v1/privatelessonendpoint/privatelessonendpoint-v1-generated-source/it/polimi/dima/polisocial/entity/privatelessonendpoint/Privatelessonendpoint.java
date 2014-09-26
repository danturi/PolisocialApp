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
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-09-26 at 06:19:59 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.privatelessonendpoint;

/**
 * Service definition for Privatelessonendpoint (v1).
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
 * This service uses {@link PrivatelessonendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Privatelessonendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the privatelessonendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://polimisocial.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "privatelessonendpoint/v1/";

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
  public Privatelessonendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Privatelessonendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getPrivateLesson".
   *
   * This request holds the parameters needed by the privatelessonendpoint server.  After setting any
   * optional parameters, call the {@link GetPrivateLesson#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetPrivateLesson getPrivateLesson(java.lang.Long id) throws java.io.IOException {
    GetPrivateLesson result = new GetPrivateLesson(id);
    initialize(result);
    return result;
  }

  public class GetPrivateLesson extends PrivatelessonendpointRequest<it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson> {

    private static final String REST_PATH = "privatelesson/{id}";

    /**
     * Create a request for the method "getPrivateLesson".
     *
     * This request holds the parameters needed by the the privatelessonendpoint server.  After
     * setting any optional parameters, call the {@link GetPrivateLesson#execute()} method to invoke
     * the remote operation. <p> {@link GetPrivateLesson#initialize(com.google.api.client.googleapis.s
     * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetPrivateLesson(java.lang.Long id) {
      super(Privatelessonendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson.class);
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
    public GetPrivateLesson setAlt(java.lang.String alt) {
      return (GetPrivateLesson) super.setAlt(alt);
    }

    @Override
    public GetPrivateLesson setFields(java.lang.String fields) {
      return (GetPrivateLesson) super.setFields(fields);
    }

    @Override
    public GetPrivateLesson setKey(java.lang.String key) {
      return (GetPrivateLesson) super.setKey(key);
    }

    @Override
    public GetPrivateLesson setOauthToken(java.lang.String oauthToken) {
      return (GetPrivateLesson) super.setOauthToken(oauthToken);
    }

    @Override
    public GetPrivateLesson setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetPrivateLesson) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetPrivateLesson setQuotaUser(java.lang.String quotaUser) {
      return (GetPrivateLesson) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetPrivateLesson setUserIp(java.lang.String userIp) {
      return (GetPrivateLesson) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetPrivateLesson setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetPrivateLesson set(String parameterName, Object value) {
      return (GetPrivateLesson) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertPrivateLesson".
   *
   * This request holds the parameters needed by the privatelessonendpoint server.  After setting any
   * optional parameters, call the {@link InsertPrivateLesson#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson}
   * @return the request
   */
  public InsertPrivateLesson insertPrivateLesson(it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson content) throws java.io.IOException {
    InsertPrivateLesson result = new InsertPrivateLesson(content);
    initialize(result);
    return result;
  }

  public class InsertPrivateLesson extends PrivatelessonendpointRequest<it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson> {

    private static final String REST_PATH = "privatelesson";

    /**
     * Create a request for the method "insertPrivateLesson".
     *
     * This request holds the parameters needed by the the privatelessonendpoint server.  After
     * setting any optional parameters, call the {@link InsertPrivateLesson#execute()} method to
     * invoke the remote operation. <p> {@link InsertPrivateLesson#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson}
     * @since 1.13
     */
    protected InsertPrivateLesson(it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson content) {
      super(Privatelessonendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson.class);
    }

    @Override
    public InsertPrivateLesson setAlt(java.lang.String alt) {
      return (InsertPrivateLesson) super.setAlt(alt);
    }

    @Override
    public InsertPrivateLesson setFields(java.lang.String fields) {
      return (InsertPrivateLesson) super.setFields(fields);
    }

    @Override
    public InsertPrivateLesson setKey(java.lang.String key) {
      return (InsertPrivateLesson) super.setKey(key);
    }

    @Override
    public InsertPrivateLesson setOauthToken(java.lang.String oauthToken) {
      return (InsertPrivateLesson) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertPrivateLesson setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertPrivateLesson) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertPrivateLesson setQuotaUser(java.lang.String quotaUser) {
      return (InsertPrivateLesson) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertPrivateLesson setUserIp(java.lang.String userIp) {
      return (InsertPrivateLesson) super.setUserIp(userIp);
    }

    @Override
    public InsertPrivateLesson set(String parameterName, Object value) {
      return (InsertPrivateLesson) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listPrivateLesson".
   *
   * This request holds the parameters needed by the privatelessonendpoint server.  After setting any
   * optional parameters, call the {@link ListPrivateLesson#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListPrivateLesson listPrivateLesson() throws java.io.IOException {
    ListPrivateLesson result = new ListPrivateLesson();
    initialize(result);
    return result;
  }

  public class ListPrivateLesson extends PrivatelessonendpointRequest<it.polimi.dima.polisocial.entity.privatelessonendpoint.model.CollectionResponsePrivateLesson> {

    private static final String REST_PATH = "privatelesson";

    /**
     * Create a request for the method "listPrivateLesson".
     *
     * This request holds the parameters needed by the the privatelessonendpoint server.  After
     * setting any optional parameters, call the {@link ListPrivateLesson#execute()} method to invoke
     * the remote operation. <p> {@link ListPrivateLesson#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListPrivateLesson() {
      super(Privatelessonendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.privatelessonendpoint.model.CollectionResponsePrivateLesson.class);
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
    public ListPrivateLesson setAlt(java.lang.String alt) {
      return (ListPrivateLesson) super.setAlt(alt);
    }

    @Override
    public ListPrivateLesson setFields(java.lang.String fields) {
      return (ListPrivateLesson) super.setFields(fields);
    }

    @Override
    public ListPrivateLesson setKey(java.lang.String key) {
      return (ListPrivateLesson) super.setKey(key);
    }

    @Override
    public ListPrivateLesson setOauthToken(java.lang.String oauthToken) {
      return (ListPrivateLesson) super.setOauthToken(oauthToken);
    }

    @Override
    public ListPrivateLesson setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListPrivateLesson) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListPrivateLesson setQuotaUser(java.lang.String quotaUser) {
      return (ListPrivateLesson) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListPrivateLesson setUserIp(java.lang.String userIp) {
      return (ListPrivateLesson) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListPrivateLesson setCursor(java.lang.String cursor) {
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

    public ListPrivateLesson setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListPrivateLesson set(String parameterName, Object value) {
      return (ListPrivateLesson) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removePrivateLesson".
   *
   * This request holds the parameters needed by the privatelessonendpoint server.  After setting any
   * optional parameters, call the {@link RemovePrivateLesson#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemovePrivateLesson removePrivateLesson(java.lang.Long id) throws java.io.IOException {
    RemovePrivateLesson result = new RemovePrivateLesson(id);
    initialize(result);
    return result;
  }

  public class RemovePrivateLesson extends PrivatelessonendpointRequest<Void> {

    private static final String REST_PATH = "privatelesson/{id}";

    /**
     * Create a request for the method "removePrivateLesson".
     *
     * This request holds the parameters needed by the the privatelessonendpoint server.  After
     * setting any optional parameters, call the {@link RemovePrivateLesson#execute()} method to
     * invoke the remote operation. <p> {@link RemovePrivateLesson#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemovePrivateLesson(java.lang.Long id) {
      super(Privatelessonendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemovePrivateLesson setAlt(java.lang.String alt) {
      return (RemovePrivateLesson) super.setAlt(alt);
    }

    @Override
    public RemovePrivateLesson setFields(java.lang.String fields) {
      return (RemovePrivateLesson) super.setFields(fields);
    }

    @Override
    public RemovePrivateLesson setKey(java.lang.String key) {
      return (RemovePrivateLesson) super.setKey(key);
    }

    @Override
    public RemovePrivateLesson setOauthToken(java.lang.String oauthToken) {
      return (RemovePrivateLesson) super.setOauthToken(oauthToken);
    }

    @Override
    public RemovePrivateLesson setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemovePrivateLesson) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemovePrivateLesson setQuotaUser(java.lang.String quotaUser) {
      return (RemovePrivateLesson) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemovePrivateLesson setUserIp(java.lang.String userIp) {
      return (RemovePrivateLesson) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemovePrivateLesson setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemovePrivateLesson set(String parameterName, Object value) {
      return (RemovePrivateLesson) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updatePrivateLesson".
   *
   * This request holds the parameters needed by the privatelessonendpoint server.  After setting any
   * optional parameters, call the {@link UpdatePrivateLesson#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson}
   * @return the request
   */
  public UpdatePrivateLesson updatePrivateLesson(it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson content) throws java.io.IOException {
    UpdatePrivateLesson result = new UpdatePrivateLesson(content);
    initialize(result);
    return result;
  }

  public class UpdatePrivateLesson extends PrivatelessonendpointRequest<it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson> {

    private static final String REST_PATH = "privatelesson";

    /**
     * Create a request for the method "updatePrivateLesson".
     *
     * This request holds the parameters needed by the the privatelessonendpoint server.  After
     * setting any optional parameters, call the {@link UpdatePrivateLesson#execute()} method to
     * invoke the remote operation. <p> {@link UpdatePrivateLesson#initialize(com.google.api.client.go
     * ogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson}
     * @since 1.13
     */
    protected UpdatePrivateLesson(it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson content) {
      super(Privatelessonendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.privatelessonendpoint.model.PrivateLesson.class);
    }

    @Override
    public UpdatePrivateLesson setAlt(java.lang.String alt) {
      return (UpdatePrivateLesson) super.setAlt(alt);
    }

    @Override
    public UpdatePrivateLesson setFields(java.lang.String fields) {
      return (UpdatePrivateLesson) super.setFields(fields);
    }

    @Override
    public UpdatePrivateLesson setKey(java.lang.String key) {
      return (UpdatePrivateLesson) super.setKey(key);
    }

    @Override
    public UpdatePrivateLesson setOauthToken(java.lang.String oauthToken) {
      return (UpdatePrivateLesson) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdatePrivateLesson setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdatePrivateLesson) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdatePrivateLesson setQuotaUser(java.lang.String quotaUser) {
      return (UpdatePrivateLesson) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdatePrivateLesson setUserIp(java.lang.String userIp) {
      return (UpdatePrivateLesson) super.setUserIp(userIp);
    }

    @Override
    public UpdatePrivateLesson set(String parameterName, Object value) {
      return (UpdatePrivateLesson) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Privatelessonendpoint}.
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

    /** Builds a new instance of {@link Privatelessonendpoint}. */
    @Override
    public Privatelessonendpoint build() {
      return new Privatelessonendpoint(this);
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
     * Set the {@link PrivatelessonendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPrivatelessonendpointRequestInitializer(
        PrivatelessonendpointRequestInitializer privatelessonendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(privatelessonendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
