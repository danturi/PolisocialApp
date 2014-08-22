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
 * on 2014-08-19 at 23:31:41 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.initiativeendpoint;

/**
 * Service definition for Initiativeendpoint (v1).
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
 * This service uses {@link InitiativeendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Initiativeendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the initiativeendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "initiativeendpoint/v1/";

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
  public Initiativeendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Initiativeendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getInitiative".
   *
   * This request holds the parameters needed by the initiativeendpoint server.  After setting any
   * optional parameters, call the {@link GetInitiative#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetInitiative getInitiative(java.lang.Long id) throws java.io.IOException {
    GetInitiative result = new GetInitiative(id);
    initialize(result);
    return result;
  }

  public class GetInitiative extends InitiativeendpointRequest<it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative> {

    private static final String REST_PATH = "initiative/{id}";

    /**
     * Create a request for the method "getInitiative".
     *
     * This request holds the parameters needed by the the initiativeendpoint server.  After setting
     * any optional parameters, call the {@link GetInitiative#execute()} method to invoke the remote
     * operation. <p> {@link GetInitiative#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetInitiative(java.lang.Long id) {
      super(Initiativeendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative.class);
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
    public GetInitiative setAlt(java.lang.String alt) {
      return (GetInitiative) super.setAlt(alt);
    }

    @Override
    public GetInitiative setFields(java.lang.String fields) {
      return (GetInitiative) super.setFields(fields);
    }

    @Override
    public GetInitiative setKey(java.lang.String key) {
      return (GetInitiative) super.setKey(key);
    }

    @Override
    public GetInitiative setOauthToken(java.lang.String oauthToken) {
      return (GetInitiative) super.setOauthToken(oauthToken);
    }

    @Override
    public GetInitiative setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetInitiative) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetInitiative setQuotaUser(java.lang.String quotaUser) {
      return (GetInitiative) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetInitiative setUserIp(java.lang.String userIp) {
      return (GetInitiative) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetInitiative setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetInitiative set(String parameterName, Object value) {
      return (GetInitiative) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertInitiative".
   *
   * This request holds the parameters needed by the initiativeendpoint server.  After setting any
   * optional parameters, call the {@link InsertInitiative#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative}
   * @return the request
   */
  public InsertInitiative insertInitiative(it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative content) throws java.io.IOException {
    InsertInitiative result = new InsertInitiative(content);
    initialize(result);
    return result;
  }

  public class InsertInitiative extends InitiativeendpointRequest<it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative> {

    private static final String REST_PATH = "initiative";

    /**
     * Create a request for the method "insertInitiative".
     *
     * This request holds the parameters needed by the the initiativeendpoint server.  After setting
     * any optional parameters, call the {@link InsertInitiative#execute()} method to invoke the
     * remote operation. <p> {@link InsertInitiative#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative}
     * @since 1.13
     */
    protected InsertInitiative(it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative content) {
      super(Initiativeendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative.class);
    }

    @Override
    public InsertInitiative setAlt(java.lang.String alt) {
      return (InsertInitiative) super.setAlt(alt);
    }

    @Override
    public InsertInitiative setFields(java.lang.String fields) {
      return (InsertInitiative) super.setFields(fields);
    }

    @Override
    public InsertInitiative setKey(java.lang.String key) {
      return (InsertInitiative) super.setKey(key);
    }

    @Override
    public InsertInitiative setOauthToken(java.lang.String oauthToken) {
      return (InsertInitiative) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertInitiative setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertInitiative) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertInitiative setQuotaUser(java.lang.String quotaUser) {
      return (InsertInitiative) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertInitiative setUserIp(java.lang.String userIp) {
      return (InsertInitiative) super.setUserIp(userIp);
    }

    @Override
    public InsertInitiative set(String parameterName, Object value) {
      return (InsertInitiative) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listInitiative".
   *
   * This request holds the parameters needed by the initiativeendpoint server.  After setting any
   * optional parameters, call the {@link ListInitiative#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListInitiative listInitiative() throws java.io.IOException {
    ListInitiative result = new ListInitiative();
    initialize(result);
    return result;
  }

  public class ListInitiative extends InitiativeendpointRequest<it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative> {

    private static final String REST_PATH = "initiative";

    /**
     * Create a request for the method "listInitiative".
     *
     * This request holds the parameters needed by the the initiativeendpoint server.  After setting
     * any optional parameters, call the {@link ListInitiative#execute()} method to invoke the remote
     * operation. <p> {@link ListInitiative#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListInitiative() {
      super(Initiativeendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative.class);
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
    public ListInitiative setAlt(java.lang.String alt) {
      return (ListInitiative) super.setAlt(alt);
    }

    @Override
    public ListInitiative setFields(java.lang.String fields) {
      return (ListInitiative) super.setFields(fields);
    }

    @Override
    public ListInitiative setKey(java.lang.String key) {
      return (ListInitiative) super.setKey(key);
    }

    @Override
    public ListInitiative setOauthToken(java.lang.String oauthToken) {
      return (ListInitiative) super.setOauthToken(oauthToken);
    }

    @Override
    public ListInitiative setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListInitiative) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListInitiative setQuotaUser(java.lang.String quotaUser) {
      return (ListInitiative) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListInitiative setUserIp(java.lang.String userIp) {
      return (ListInitiative) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListInitiative setCursor(java.lang.String cursor) {
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

    public ListInitiative setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListInitiative set(String parameterName, Object value) {
      return (ListInitiative) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeInitiative".
   *
   * This request holds the parameters needed by the initiativeendpoint server.  After setting any
   * optional parameters, call the {@link RemoveInitiative#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveInitiative removeInitiative(java.lang.Long id) throws java.io.IOException {
    RemoveInitiative result = new RemoveInitiative(id);
    initialize(result);
    return result;
  }

  public class RemoveInitiative extends InitiativeendpointRequest<Void> {

    private static final String REST_PATH = "initiative/{id}";

    /**
     * Create a request for the method "removeInitiative".
     *
     * This request holds the parameters needed by the the initiativeendpoint server.  After setting
     * any optional parameters, call the {@link RemoveInitiative#execute()} method to invoke the
     * remote operation. <p> {@link RemoveInitiative#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveInitiative(java.lang.Long id) {
      super(Initiativeendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveInitiative setAlt(java.lang.String alt) {
      return (RemoveInitiative) super.setAlt(alt);
    }

    @Override
    public RemoveInitiative setFields(java.lang.String fields) {
      return (RemoveInitiative) super.setFields(fields);
    }

    @Override
    public RemoveInitiative setKey(java.lang.String key) {
      return (RemoveInitiative) super.setKey(key);
    }

    @Override
    public RemoveInitiative setOauthToken(java.lang.String oauthToken) {
      return (RemoveInitiative) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveInitiative setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveInitiative) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveInitiative setQuotaUser(java.lang.String quotaUser) {
      return (RemoveInitiative) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveInitiative setUserIp(java.lang.String userIp) {
      return (RemoveInitiative) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveInitiative setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveInitiative set(String parameterName, Object value) {
      return (RemoveInitiative) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateInitiative".
   *
   * This request holds the parameters needed by the initiativeendpoint server.  After setting any
   * optional parameters, call the {@link UpdateInitiative#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative}
   * @return the request
   */
  public UpdateInitiative updateInitiative(it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative content) throws java.io.IOException {
    UpdateInitiative result = new UpdateInitiative(content);
    initialize(result);
    return result;
  }

  public class UpdateInitiative extends InitiativeendpointRequest<it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative> {

    private static final String REST_PATH = "initiative";

    /**
     * Create a request for the method "updateInitiative".
     *
     * This request holds the parameters needed by the the initiativeendpoint server.  After setting
     * any optional parameters, call the {@link UpdateInitiative#execute()} method to invoke the
     * remote operation. <p> {@link UpdateInitiative#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative}
     * @since 1.13
     */
    protected UpdateInitiative(it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative content) {
      super(Initiativeendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative.class);
    }

    @Override
    public UpdateInitiative setAlt(java.lang.String alt) {
      return (UpdateInitiative) super.setAlt(alt);
    }

    @Override
    public UpdateInitiative setFields(java.lang.String fields) {
      return (UpdateInitiative) super.setFields(fields);
    }

    @Override
    public UpdateInitiative setKey(java.lang.String key) {
      return (UpdateInitiative) super.setKey(key);
    }

    @Override
    public UpdateInitiative setOauthToken(java.lang.String oauthToken) {
      return (UpdateInitiative) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateInitiative setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateInitiative) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateInitiative setQuotaUser(java.lang.String quotaUser) {
      return (UpdateInitiative) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateInitiative setUserIp(java.lang.String userIp) {
      return (UpdateInitiative) super.setUserIp(userIp);
    }

    @Override
    public UpdateInitiative set(String parameterName, Object value) {
      return (UpdateInitiative) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Initiativeendpoint}.
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

    /** Builds a new instance of {@link Initiativeendpoint}. */
    @Override
    public Initiativeendpoint build() {
      return new Initiativeendpoint(this);
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
     * Set the {@link InitiativeendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setInitiativeendpointRequestInitializer(
        InitiativeendpointRequestInitializer initiativeendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(initiativeendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
