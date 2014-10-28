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
 * on 2014-10-28 at 10:56:37 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.hitonendpoint;

/**
 * Service definition for Hitonendpoint (v1).
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
 * This service uses {@link HitonendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Hitonendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the hitonendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "hitonendpoint/v1/";

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
  public Hitonendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Hitonendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link GetHitOn#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetHitOn getHitOn(java.lang.Long id) throws java.io.IOException {
    GetHitOn result = new GetHitOn(id);
    initialize(result);
    return result;
  }

  public class GetHitOn extends HitonendpointRequest<it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn> {

    private static final String REST_PATH = "hiton/{id}";

    /**
     * Create a request for the method "getHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link GetHitOn#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetHitOn(java.lang.Long id) {
      super(Hitonendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn.class);
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
    public GetHitOn setAlt(java.lang.String alt) {
      return (GetHitOn) super.setAlt(alt);
    }

    @Override
    public GetHitOn setFields(java.lang.String fields) {
      return (GetHitOn) super.setFields(fields);
    }

    @Override
    public GetHitOn setKey(java.lang.String key) {
      return (GetHitOn) super.setKey(key);
    }

    @Override
    public GetHitOn setOauthToken(java.lang.String oauthToken) {
      return (GetHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public GetHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetHitOn setQuotaUser(java.lang.String quotaUser) {
      return (GetHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetHitOn setUserIp(java.lang.String userIp) {
      return (GetHitOn) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetHitOn setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetHitOn set(String parameterName, Object value) {
      return (GetHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link GetUserHitOn#execute()} method to invoke the remote operation.
   *
   * @param postId
   * @return the request
   */
  public GetUserHitOn getUserHitOn(java.lang.Long postId) throws java.io.IOException {
    GetUserHitOn result = new GetUserHitOn(postId);
    initialize(result);
    return result;
  }

  public class GetUserHitOn extends HitonendpointRequest<it.polimi.dima.polisocial.entity.hitonendpoint.model.CollectionResponseHitOn> {

    private static final String REST_PATH = "collectionresponse_hiton/{postId}";

    /**
     * Create a request for the method "getUserHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link GetUserHitOn#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetUserHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param postId
     * @since 1.13
     */
    protected GetUserHitOn(java.lang.Long postId) {
      super(Hitonendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.hitonendpoint.model.CollectionResponseHitOn.class);
      this.postId = com.google.api.client.util.Preconditions.checkNotNull(postId, "Required parameter postId must be specified.");
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
    public GetUserHitOn setAlt(java.lang.String alt) {
      return (GetUserHitOn) super.setAlt(alt);
    }

    @Override
    public GetUserHitOn setFields(java.lang.String fields) {
      return (GetUserHitOn) super.setFields(fields);
    }

    @Override
    public GetUserHitOn setKey(java.lang.String key) {
      return (GetUserHitOn) super.setKey(key);
    }

    @Override
    public GetUserHitOn setOauthToken(java.lang.String oauthToken) {
      return (GetUserHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserHitOn setQuotaUser(java.lang.String quotaUser) {
      return (GetUserHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserHitOn setUserIp(java.lang.String userIp) {
      return (GetUserHitOn) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long postId;

    /**

     */
    public java.lang.Long getPostId() {
      return postId;
    }

    public GetUserHitOn setPostId(java.lang.Long postId) {
      this.postId = postId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public GetUserHitOn setCursor(java.lang.String cursor) {
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

    public GetUserHitOn setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public GetUserHitOn set(String parameterName, Object value) {
      return (GetUserHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link InsertHitOn#execute()} method to invoke the remote operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
   * @return the request
   */
  public InsertHitOn insertHitOn(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) throws java.io.IOException {
    InsertHitOn result = new InsertHitOn(content);
    initialize(result);
    return result;
  }

  public class InsertHitOn extends HitonendpointRequest<it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn> {

    private static final String REST_PATH = "hiton";

    /**
     * Create a request for the method "insertHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link InsertHitOn#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
     * @since 1.13
     */
    protected InsertHitOn(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) {
      super(Hitonendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn.class);
    }

    @Override
    public InsertHitOn setAlt(java.lang.String alt) {
      return (InsertHitOn) super.setAlt(alt);
    }

    @Override
    public InsertHitOn setFields(java.lang.String fields) {
      return (InsertHitOn) super.setFields(fields);
    }

    @Override
    public InsertHitOn setKey(java.lang.String key) {
      return (InsertHitOn) super.setKey(key);
    }

    @Override
    public InsertHitOn setOauthToken(java.lang.String oauthToken) {
      return (InsertHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertHitOn setQuotaUser(java.lang.String quotaUser) {
      return (InsertHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertHitOn setUserIp(java.lang.String userIp) {
      return (InsertHitOn) super.setUserIp(userIp);
    }

    @Override
    public InsertHitOn set(String parameterName, Object value) {
      return (InsertHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link ListHitOn#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListHitOn listHitOn() throws java.io.IOException {
    ListHitOn result = new ListHitOn();
    initialize(result);
    return result;
  }

  public class ListHitOn extends HitonendpointRequest<it.polimi.dima.polisocial.entity.hitonendpoint.model.CollectionResponseHitOn> {

    private static final String REST_PATH = "hiton";

    /**
     * Create a request for the method "listHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link ListHitOn#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListHitOn() {
      super(Hitonendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.hitonendpoint.model.CollectionResponseHitOn.class);
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
    public ListHitOn setAlt(java.lang.String alt) {
      return (ListHitOn) super.setAlt(alt);
    }

    @Override
    public ListHitOn setFields(java.lang.String fields) {
      return (ListHitOn) super.setFields(fields);
    }

    @Override
    public ListHitOn setKey(java.lang.String key) {
      return (ListHitOn) super.setKey(key);
    }

    @Override
    public ListHitOn setOauthToken(java.lang.String oauthToken) {
      return (ListHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public ListHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListHitOn setQuotaUser(java.lang.String quotaUser) {
      return (ListHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListHitOn setUserIp(java.lang.String userIp) {
      return (ListHitOn) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListHitOn setCursor(java.lang.String cursor) {
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

    public ListHitOn setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListHitOn set(String parameterName, Object value) {
      return (ListHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link RemoveHitOn#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveHitOn removeHitOn(java.lang.Long id) throws java.io.IOException {
    RemoveHitOn result = new RemoveHitOn(id);
    initialize(result);
    return result;
  }

  public class RemoveHitOn extends HitonendpointRequest<Void> {

    private static final String REST_PATH = "hiton/{id}";

    /**
     * Create a request for the method "removeHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link RemoveHitOn#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveHitOn(java.lang.Long id) {
      super(Hitonendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveHitOn setAlt(java.lang.String alt) {
      return (RemoveHitOn) super.setAlt(alt);
    }

    @Override
    public RemoveHitOn setFields(java.lang.String fields) {
      return (RemoveHitOn) super.setFields(fields);
    }

    @Override
    public RemoveHitOn setKey(java.lang.String key) {
      return (RemoveHitOn) super.setKey(key);
    }

    @Override
    public RemoveHitOn setOauthToken(java.lang.String oauthToken) {
      return (RemoveHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveHitOn setQuotaUser(java.lang.String quotaUser) {
      return (RemoveHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveHitOn setUserIp(java.lang.String userIp) {
      return (RemoveHitOn) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveHitOn setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveHitOn set(String parameterName, Object value) {
      return (RemoveHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "sendHitOnNotification".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link SendHitOnNotification#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
   * @return the request
   */
  public SendHitOnNotification sendHitOnNotification(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) throws java.io.IOException {
    SendHitOnNotification result = new SendHitOnNotification(content);
    initialize(result);
    return result;
  }

  public class SendHitOnNotification extends HitonendpointRequest<Void> {

    private static final String REST_PATH = "sendHitOnNotification";

    /**
     * Create a request for the method "sendHitOnNotification".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link SendHitOnNotification#execute()} method to invoke the
     * remote operation. <p> {@link SendHitOnNotification#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
     * @since 1.13
     */
    protected SendHitOnNotification(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) {
      super(Hitonendpoint.this, "POST", REST_PATH, content, Void.class);
    }

    @Override
    public SendHitOnNotification setAlt(java.lang.String alt) {
      return (SendHitOnNotification) super.setAlt(alt);
    }

    @Override
    public SendHitOnNotification setFields(java.lang.String fields) {
      return (SendHitOnNotification) super.setFields(fields);
    }

    @Override
    public SendHitOnNotification setKey(java.lang.String key) {
      return (SendHitOnNotification) super.setKey(key);
    }

    @Override
    public SendHitOnNotification setOauthToken(java.lang.String oauthToken) {
      return (SendHitOnNotification) super.setOauthToken(oauthToken);
    }

    @Override
    public SendHitOnNotification setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SendHitOnNotification) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SendHitOnNotification setQuotaUser(java.lang.String quotaUser) {
      return (SendHitOnNotification) super.setQuotaUser(quotaUser);
    }

    @Override
    public SendHitOnNotification setUserIp(java.lang.String userIp) {
      return (SendHitOnNotification) super.setUserIp(userIp);
    }

    @Override
    public SendHitOnNotification set(String parameterName, Object value) {
      return (SendHitOnNotification) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateHitOn".
   *
   * This request holds the parameters needed by the hitonendpoint server.  After setting any optional
   * parameters, call the {@link UpdateHitOn#execute()} method to invoke the remote operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
   * @return the request
   */
  public UpdateHitOn updateHitOn(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) throws java.io.IOException {
    UpdateHitOn result = new UpdateHitOn(content);
    initialize(result);
    return result;
  }

  public class UpdateHitOn extends HitonendpointRequest<it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn> {

    private static final String REST_PATH = "hiton";

    /**
     * Create a request for the method "updateHitOn".
     *
     * This request holds the parameters needed by the the hitonendpoint server.  After setting any
     * optional parameters, call the {@link UpdateHitOn#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateHitOn#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn}
     * @since 1.13
     */
    protected UpdateHitOn(it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn content) {
      super(Hitonendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn.class);
    }

    @Override
    public UpdateHitOn setAlt(java.lang.String alt) {
      return (UpdateHitOn) super.setAlt(alt);
    }

    @Override
    public UpdateHitOn setFields(java.lang.String fields) {
      return (UpdateHitOn) super.setFields(fields);
    }

    @Override
    public UpdateHitOn setKey(java.lang.String key) {
      return (UpdateHitOn) super.setKey(key);
    }

    @Override
    public UpdateHitOn setOauthToken(java.lang.String oauthToken) {
      return (UpdateHitOn) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateHitOn setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateHitOn) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateHitOn setQuotaUser(java.lang.String quotaUser) {
      return (UpdateHitOn) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateHitOn setUserIp(java.lang.String userIp) {
      return (UpdateHitOn) super.setUserIp(userIp);
    }

    @Override
    public UpdateHitOn set(String parameterName, Object value) {
      return (UpdateHitOn) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Hitonendpoint}.
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

    /** Builds a new instance of {@link Hitonendpoint}. */
    @Override
    public Hitonendpoint build() {
      return new Hitonendpoint(this);
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
     * Set the {@link HitonendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setHitonendpointRequestInitializer(
        HitonendpointRequestInitializer hitonendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(hitonendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
