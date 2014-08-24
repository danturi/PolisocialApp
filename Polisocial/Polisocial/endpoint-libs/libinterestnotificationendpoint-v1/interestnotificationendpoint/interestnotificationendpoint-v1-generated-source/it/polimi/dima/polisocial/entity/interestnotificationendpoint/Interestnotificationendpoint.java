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
 * on 2014-08-24 at 01:08:08 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.interestnotificationendpoint;

/**
 * Service definition for Interestnotificationendpoint (v1).
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
 * This service uses {@link InterestnotificationendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Interestnotificationendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the interestnotificationendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "interestnotificationendpoint/v1/";

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
  public Interestnotificationendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Interestnotificationendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getInterestNotification".
   *
   * This request holds the parameters needed by the interestnotificationendpoint server.  After
   * setting any optional parameters, call the {@link GetInterestNotification#execute()} method to
   * invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetInterestNotification getInterestNotification(java.lang.Long id) throws java.io.IOException {
    GetInterestNotification result = new GetInterestNotification(id);
    initialize(result);
    return result;
  }

  public class GetInterestNotification extends InterestnotificationendpointRequest<it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification> {

    private static final String REST_PATH = "interestnotification/{id}";

    /**
     * Create a request for the method "getInterestNotification".
     *
     * This request holds the parameters needed by the the interestnotificationendpoint server.  After
     * setting any optional parameters, call the {@link GetInterestNotification#execute()} method to
     * invoke the remote operation. <p> {@link GetInterestNotification#initialize(com.google.api.clien
     * t.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetInterestNotification(java.lang.Long id) {
      super(Interestnotificationendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification.class);
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
    public GetInterestNotification setAlt(java.lang.String alt) {
      return (GetInterestNotification) super.setAlt(alt);
    }

    @Override
    public GetInterestNotification setFields(java.lang.String fields) {
      return (GetInterestNotification) super.setFields(fields);
    }

    @Override
    public GetInterestNotification setKey(java.lang.String key) {
      return (GetInterestNotification) super.setKey(key);
    }

    @Override
    public GetInterestNotification setOauthToken(java.lang.String oauthToken) {
      return (GetInterestNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public GetInterestNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetInterestNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetInterestNotification setQuotaUser(java.lang.String quotaUser) {
      return (GetInterestNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetInterestNotification setUserIp(java.lang.String userIp) {
      return (GetInterestNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetInterestNotification setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetInterestNotification set(String parameterName, Object value) {
      return (GetInterestNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertInterestNotification".
   *
   * This request holds the parameters needed by the interestnotificationendpoint server.  After
   * setting any optional parameters, call the {@link InsertInterestNotification#execute()} method to
   * invoke the remote operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification}
   * @return the request
   */
  public InsertInterestNotification insertInterestNotification(it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification content) throws java.io.IOException {
    InsertInterestNotification result = new InsertInterestNotification(content);
    initialize(result);
    return result;
  }

  public class InsertInterestNotification extends InterestnotificationendpointRequest<it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification> {

    private static final String REST_PATH = "interestnotification";

    /**
     * Create a request for the method "insertInterestNotification".
     *
     * This request holds the parameters needed by the the interestnotificationendpoint server.  After
     * setting any optional parameters, call the {@link InsertInterestNotification#execute()} method
     * to invoke the remote operation. <p> {@link InsertInterestNotification#initialize(com.google.api
     * .client.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification}
     * @since 1.13
     */
    protected InsertInterestNotification(it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification content) {
      super(Interestnotificationendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification.class);
    }

    @Override
    public InsertInterestNotification setAlt(java.lang.String alt) {
      return (InsertInterestNotification) super.setAlt(alt);
    }

    @Override
    public InsertInterestNotification setFields(java.lang.String fields) {
      return (InsertInterestNotification) super.setFields(fields);
    }

    @Override
    public InsertInterestNotification setKey(java.lang.String key) {
      return (InsertInterestNotification) super.setKey(key);
    }

    @Override
    public InsertInterestNotification setOauthToken(java.lang.String oauthToken) {
      return (InsertInterestNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertInterestNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertInterestNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertInterestNotification setQuotaUser(java.lang.String quotaUser) {
      return (InsertInterestNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertInterestNotification setUserIp(java.lang.String userIp) {
      return (InsertInterestNotification) super.setUserIp(userIp);
    }

    @Override
    public InsertInterestNotification set(String parameterName, Object value) {
      return (InsertInterestNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listInterestNotification".
   *
   * This request holds the parameters needed by the interestnotificationendpoint server.  After
   * setting any optional parameters, call the {@link ListInterestNotification#execute()} method to
   * invoke the remote operation.
   *
   * @return the request
   */
  public ListInterestNotification listInterestNotification() throws java.io.IOException {
    ListInterestNotification result = new ListInterestNotification();
    initialize(result);
    return result;
  }

  public class ListInterestNotification extends InterestnotificationendpointRequest<it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.CollectionResponseInterestNotification> {

    private static final String REST_PATH = "interestnotification";

    /**
     * Create a request for the method "listInterestNotification".
     *
     * This request holds the parameters needed by the the interestnotificationendpoint server.  After
     * setting any optional parameters, call the {@link ListInterestNotification#execute()} method to
     * invoke the remote operation. <p> {@link ListInterestNotification#initialize(com.google.api.clie
     * nt.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListInterestNotification() {
      super(Interestnotificationendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.CollectionResponseInterestNotification.class);
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
    public ListInterestNotification setAlt(java.lang.String alt) {
      return (ListInterestNotification) super.setAlt(alt);
    }

    @Override
    public ListInterestNotification setFields(java.lang.String fields) {
      return (ListInterestNotification) super.setFields(fields);
    }

    @Override
    public ListInterestNotification setKey(java.lang.String key) {
      return (ListInterestNotification) super.setKey(key);
    }

    @Override
    public ListInterestNotification setOauthToken(java.lang.String oauthToken) {
      return (ListInterestNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public ListInterestNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListInterestNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListInterestNotification setQuotaUser(java.lang.String quotaUser) {
      return (ListInterestNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListInterestNotification setUserIp(java.lang.String userIp) {
      return (ListInterestNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListInterestNotification setCursor(java.lang.String cursor) {
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

    public ListInterestNotification setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListInterestNotification set(String parameterName, Object value) {
      return (ListInterestNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeInterestNotification".
   *
   * This request holds the parameters needed by the interestnotificationendpoint server.  After
   * setting any optional parameters, call the {@link RemoveInterestNotification#execute()} method to
   * invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveInterestNotification removeInterestNotification(java.lang.Long id) throws java.io.IOException {
    RemoveInterestNotification result = new RemoveInterestNotification(id);
    initialize(result);
    return result;
  }

  public class RemoveInterestNotification extends InterestnotificationendpointRequest<Void> {

    private static final String REST_PATH = "interestnotification/{id}";

    /**
     * Create a request for the method "removeInterestNotification".
     *
     * This request holds the parameters needed by the the interestnotificationendpoint server.  After
     * setting any optional parameters, call the {@link RemoveInterestNotification#execute()} method
     * to invoke the remote operation. <p> {@link RemoveInterestNotification#initialize(com.google.api
     * .client.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveInterestNotification(java.lang.Long id) {
      super(Interestnotificationendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveInterestNotification setAlt(java.lang.String alt) {
      return (RemoveInterestNotification) super.setAlt(alt);
    }

    @Override
    public RemoveInterestNotification setFields(java.lang.String fields) {
      return (RemoveInterestNotification) super.setFields(fields);
    }

    @Override
    public RemoveInterestNotification setKey(java.lang.String key) {
      return (RemoveInterestNotification) super.setKey(key);
    }

    @Override
    public RemoveInterestNotification setOauthToken(java.lang.String oauthToken) {
      return (RemoveInterestNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveInterestNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveInterestNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveInterestNotification setQuotaUser(java.lang.String quotaUser) {
      return (RemoveInterestNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveInterestNotification setUserIp(java.lang.String userIp) {
      return (RemoveInterestNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveInterestNotification setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveInterestNotification set(String parameterName, Object value) {
      return (RemoveInterestNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateInterestNotification".
   *
   * This request holds the parameters needed by the interestnotificationendpoint server.  After
   * setting any optional parameters, call the {@link UpdateInterestNotification#execute()} method to
   * invoke the remote operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification}
   * @return the request
   */
  public UpdateInterestNotification updateInterestNotification(it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification content) throws java.io.IOException {
    UpdateInterestNotification result = new UpdateInterestNotification(content);
    initialize(result);
    return result;
  }

  public class UpdateInterestNotification extends InterestnotificationendpointRequest<it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification> {

    private static final String REST_PATH = "interestnotification";

    /**
     * Create a request for the method "updateInterestNotification".
     *
     * This request holds the parameters needed by the the interestnotificationendpoint server.  After
     * setting any optional parameters, call the {@link UpdateInterestNotification#execute()} method
     * to invoke the remote operation. <p> {@link UpdateInterestNotification#initialize(com.google.api
     * .client.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification}
     * @since 1.13
     */
    protected UpdateInterestNotification(it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification content) {
      super(Interestnotificationendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.interestnotificationendpoint.model.InterestNotification.class);
    }

    @Override
    public UpdateInterestNotification setAlt(java.lang.String alt) {
      return (UpdateInterestNotification) super.setAlt(alt);
    }

    @Override
    public UpdateInterestNotification setFields(java.lang.String fields) {
      return (UpdateInterestNotification) super.setFields(fields);
    }

    @Override
    public UpdateInterestNotification setKey(java.lang.String key) {
      return (UpdateInterestNotification) super.setKey(key);
    }

    @Override
    public UpdateInterestNotification setOauthToken(java.lang.String oauthToken) {
      return (UpdateInterestNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateInterestNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateInterestNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateInterestNotification setQuotaUser(java.lang.String quotaUser) {
      return (UpdateInterestNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateInterestNotification setUserIp(java.lang.String userIp) {
      return (UpdateInterestNotification) super.setUserIp(userIp);
    }

    @Override
    public UpdateInterestNotification set(String parameterName, Object value) {
      return (UpdateInterestNotification) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Interestnotificationendpoint}.
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

    /** Builds a new instance of {@link Interestnotificationendpoint}. */
    @Override
    public Interestnotificationendpoint build() {
      return new Interestnotificationendpoint(this);
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
     * Set the {@link InterestnotificationendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setInterestnotificationendpointRequestInitializer(
        InterestnotificationendpointRequestInitializer interestnotificationendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(interestnotificationendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
