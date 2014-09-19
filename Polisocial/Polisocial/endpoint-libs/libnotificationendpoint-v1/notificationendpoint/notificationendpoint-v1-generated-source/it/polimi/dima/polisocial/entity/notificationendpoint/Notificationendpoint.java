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
 * on 2014-09-19 at 12:20:30 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.notificationendpoint;

/**
 * Service definition for Notificationendpoint (v1).
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
 * This service uses {@link NotificationendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Notificationendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the notificationendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "notificationendpoint/v1/";

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
  public Notificationendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Notificationendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getNotification".
   *
   * This request holds the parameters needed by the notificationendpoint server.  After setting any
   * optional parameters, call the {@link GetNotification#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetNotification getNotification(java.lang.Long id) throws java.io.IOException {
    GetNotification result = new GetNotification(id);
    initialize(result);
    return result;
  }

  public class GetNotification extends NotificationendpointRequest<it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification> {

    private static final String REST_PATH = "notification/{id}";

    /**
     * Create a request for the method "getNotification".
     *
     * This request holds the parameters needed by the the notificationendpoint server.  After setting
     * any optional parameters, call the {@link GetNotification#execute()} method to invoke the remote
     * operation. <p> {@link GetNotification#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetNotification(java.lang.Long id) {
      super(Notificationendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification.class);
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
    public GetNotification setAlt(java.lang.String alt) {
      return (GetNotification) super.setAlt(alt);
    }

    @Override
    public GetNotification setFields(java.lang.String fields) {
      return (GetNotification) super.setFields(fields);
    }

    @Override
    public GetNotification setKey(java.lang.String key) {
      return (GetNotification) super.setKey(key);
    }

    @Override
    public GetNotification setOauthToken(java.lang.String oauthToken) {
      return (GetNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public GetNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetNotification setQuotaUser(java.lang.String quotaUser) {
      return (GetNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetNotification setUserIp(java.lang.String userIp) {
      return (GetNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetNotification setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetNotification set(String parameterName, Object value) {
      return (GetNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertNotification".
   *
   * This request holds the parameters needed by the notificationendpoint server.  After setting any
   * optional parameters, call the {@link InsertNotification#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification}
   * @return the request
   */
  public InsertNotification insertNotification(it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification content) throws java.io.IOException {
    InsertNotification result = new InsertNotification(content);
    initialize(result);
    return result;
  }

  public class InsertNotification extends NotificationendpointRequest<it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification> {

    private static final String REST_PATH = "notification";

    /**
     * Create a request for the method "insertNotification".
     *
     * This request holds the parameters needed by the the notificationendpoint server.  After setting
     * any optional parameters, call the {@link InsertNotification#execute()} method to invoke the
     * remote operation. <p> {@link InsertNotification#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification}
     * @since 1.13
     */
    protected InsertNotification(it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification content) {
      super(Notificationendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification.class);
    }

    @Override
    public InsertNotification setAlt(java.lang.String alt) {
      return (InsertNotification) super.setAlt(alt);
    }

    @Override
    public InsertNotification setFields(java.lang.String fields) {
      return (InsertNotification) super.setFields(fields);
    }

    @Override
    public InsertNotification setKey(java.lang.String key) {
      return (InsertNotification) super.setKey(key);
    }

    @Override
    public InsertNotification setOauthToken(java.lang.String oauthToken) {
      return (InsertNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertNotification setQuotaUser(java.lang.String quotaUser) {
      return (InsertNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertNotification setUserIp(java.lang.String userIp) {
      return (InsertNotification) super.setUserIp(userIp);
    }

    @Override
    public InsertNotification set(String parameterName, Object value) {
      return (InsertNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listNotification".
   *
   * This request holds the parameters needed by the notificationendpoint server.  After setting any
   * optional parameters, call the {@link ListNotification#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListNotification listNotification() throws java.io.IOException {
    ListNotification result = new ListNotification();
    initialize(result);
    return result;
  }

  public class ListNotification extends NotificationendpointRequest<it.polimi.dima.polisocial.entity.notificationendpoint.model.CollectionResponseNotification> {

    private static final String REST_PATH = "notification";

    /**
     * Create a request for the method "listNotification".
     *
     * This request holds the parameters needed by the the notificationendpoint server.  After setting
     * any optional parameters, call the {@link ListNotification#execute()} method to invoke the
     * remote operation. <p> {@link ListNotification#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListNotification() {
      super(Notificationendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.notificationendpoint.model.CollectionResponseNotification.class);
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
    public ListNotification setAlt(java.lang.String alt) {
      return (ListNotification) super.setAlt(alt);
    }

    @Override
    public ListNotification setFields(java.lang.String fields) {
      return (ListNotification) super.setFields(fields);
    }

    @Override
    public ListNotification setKey(java.lang.String key) {
      return (ListNotification) super.setKey(key);
    }

    @Override
    public ListNotification setOauthToken(java.lang.String oauthToken) {
      return (ListNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public ListNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListNotification setQuotaUser(java.lang.String quotaUser) {
      return (ListNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListNotification setUserIp(java.lang.String userIp) {
      return (ListNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListNotification setCursor(java.lang.String cursor) {
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

    public ListNotification setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListNotification set(String parameterName, Object value) {
      return (ListNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeNotification".
   *
   * This request holds the parameters needed by the notificationendpoint server.  After setting any
   * optional parameters, call the {@link RemoveNotification#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveNotification removeNotification(java.lang.Long id) throws java.io.IOException {
    RemoveNotification result = new RemoveNotification(id);
    initialize(result);
    return result;
  }

  public class RemoveNotification extends NotificationendpointRequest<Void> {

    private static final String REST_PATH = "notification/{id}";

    /**
     * Create a request for the method "removeNotification".
     *
     * This request holds the parameters needed by the the notificationendpoint server.  After setting
     * any optional parameters, call the {@link RemoveNotification#execute()} method to invoke the
     * remote operation. <p> {@link RemoveNotification#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveNotification(java.lang.Long id) {
      super(Notificationendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveNotification setAlt(java.lang.String alt) {
      return (RemoveNotification) super.setAlt(alt);
    }

    @Override
    public RemoveNotification setFields(java.lang.String fields) {
      return (RemoveNotification) super.setFields(fields);
    }

    @Override
    public RemoveNotification setKey(java.lang.String key) {
      return (RemoveNotification) super.setKey(key);
    }

    @Override
    public RemoveNotification setOauthToken(java.lang.String oauthToken) {
      return (RemoveNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveNotification setQuotaUser(java.lang.String quotaUser) {
      return (RemoveNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveNotification setUserIp(java.lang.String userIp) {
      return (RemoveNotification) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveNotification setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveNotification set(String parameterName, Object value) {
      return (RemoveNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateNotification".
   *
   * This request holds the parameters needed by the notificationendpoint server.  After setting any
   * optional parameters, call the {@link UpdateNotification#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification}
   * @return the request
   */
  public UpdateNotification updateNotification(it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification content) throws java.io.IOException {
    UpdateNotification result = new UpdateNotification(content);
    initialize(result);
    return result;
  }

  public class UpdateNotification extends NotificationendpointRequest<it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification> {

    private static final String REST_PATH = "notification";

    /**
     * Create a request for the method "updateNotification".
     *
     * This request holds the parameters needed by the the notificationendpoint server.  After setting
     * any optional parameters, call the {@link UpdateNotification#execute()} method to invoke the
     * remote operation. <p> {@link UpdateNotification#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification}
     * @since 1.13
     */
    protected UpdateNotification(it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification content) {
      super(Notificationendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification.class);
    }

    @Override
    public UpdateNotification setAlt(java.lang.String alt) {
      return (UpdateNotification) super.setAlt(alt);
    }

    @Override
    public UpdateNotification setFields(java.lang.String fields) {
      return (UpdateNotification) super.setFields(fields);
    }

    @Override
    public UpdateNotification setKey(java.lang.String key) {
      return (UpdateNotification) super.setKey(key);
    }

    @Override
    public UpdateNotification setOauthToken(java.lang.String oauthToken) {
      return (UpdateNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateNotification setQuotaUser(java.lang.String quotaUser) {
      return (UpdateNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateNotification setUserIp(java.lang.String userIp) {
      return (UpdateNotification) super.setUserIp(userIp);
    }

    @Override
    public UpdateNotification set(String parameterName, Object value) {
      return (UpdateNotification) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Notificationendpoint}.
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

    /** Builds a new instance of {@link Notificationendpoint}. */
    @Override
    public Notificationendpoint build() {
      return new Notificationendpoint(this);
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
     * Set the {@link NotificationendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setNotificationendpointRequestInitializer(
        NotificationendpointRequestInitializer notificationendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(notificationendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
