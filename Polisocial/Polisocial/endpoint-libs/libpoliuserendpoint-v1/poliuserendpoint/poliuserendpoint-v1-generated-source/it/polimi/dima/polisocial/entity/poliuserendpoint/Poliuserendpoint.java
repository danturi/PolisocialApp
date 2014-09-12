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
 * on 2014-09-12 at 13:41:32 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.poliuserendpoint;

/**
 * Service definition for Poliuserendpoint (v1).
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
 * This service uses {@link PoliuserendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Poliuserendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the poliuserendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "poliuserendpoint/v1/";

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
  public Poliuserendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Poliuserendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "checkCredentials".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link CheckCredentials#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @param password
   * @return the request
   */
  public CheckCredentials checkCredentials(java.lang.String email, java.lang.String password) throws java.io.IOException {
    CheckCredentials result = new CheckCredentials(email, password);
    initialize(result);
    return result;
  }

  public class CheckCredentials extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "checkCredentials/{email}/{password}";

    /**
     * Create a request for the method "checkCredentials".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link CheckCredentials#execute()} method to invoke the remote
     * operation. <p> {@link CheckCredentials#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @since 1.13
     */
    protected CheckCredentials(java.lang.String email, java.lang.String password) {
      super(Poliuserendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
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
    public CheckCredentials setAlt(java.lang.String alt) {
      return (CheckCredentials) super.setAlt(alt);
    }

    @Override
    public CheckCredentials setFields(java.lang.String fields) {
      return (CheckCredentials) super.setFields(fields);
    }

    @Override
    public CheckCredentials setKey(java.lang.String key) {
      return (CheckCredentials) super.setKey(key);
    }

    @Override
    public CheckCredentials setOauthToken(java.lang.String oauthToken) {
      return (CheckCredentials) super.setOauthToken(oauthToken);
    }

    @Override
    public CheckCredentials setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CheckCredentials) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CheckCredentials setQuotaUser(java.lang.String quotaUser) {
      return (CheckCredentials) super.setQuotaUser(quotaUser);
    }

    @Override
    public CheckCredentials setUserIp(java.lang.String userIp) {
      return (CheckCredentials) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public CheckCredentials setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public CheckCredentials setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public CheckCredentials set(String parameterName, Object value) {
      return (CheckCredentials) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "checkForDuplicateEmail".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link CheckForDuplicateEmail#execute()} method to invoke the
   * remote operation.
   *
   * @param email
   * @return the request
   */
  public CheckForDuplicateEmail checkForDuplicateEmail(java.lang.String email) throws java.io.IOException {
    CheckForDuplicateEmail result = new CheckForDuplicateEmail(email);
    initialize(result);
    return result;
  }

  public class CheckForDuplicateEmail extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "checkForDuplicateEmail/{email}";

    /**
     * Create a request for the method "checkForDuplicateEmail".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link CheckForDuplicateEmail#execute()} method to invoke the
     * remote operation. <p> {@link CheckForDuplicateEmail#initialize(com.google.api.client.googleapis
     * .services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected CheckForDuplicateEmail(java.lang.String email) {
      super(Poliuserendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
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
    public CheckForDuplicateEmail setAlt(java.lang.String alt) {
      return (CheckForDuplicateEmail) super.setAlt(alt);
    }

    @Override
    public CheckForDuplicateEmail setFields(java.lang.String fields) {
      return (CheckForDuplicateEmail) super.setFields(fields);
    }

    @Override
    public CheckForDuplicateEmail setKey(java.lang.String key) {
      return (CheckForDuplicateEmail) super.setKey(key);
    }

    @Override
    public CheckForDuplicateEmail setOauthToken(java.lang.String oauthToken) {
      return (CheckForDuplicateEmail) super.setOauthToken(oauthToken);
    }

    @Override
    public CheckForDuplicateEmail setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CheckForDuplicateEmail) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CheckForDuplicateEmail setQuotaUser(java.lang.String quotaUser) {
      return (CheckForDuplicateEmail) super.setQuotaUser(quotaUser);
    }

    @Override
    public CheckForDuplicateEmail setUserIp(java.lang.String userIp) {
      return (CheckForDuplicateEmail) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public CheckForDuplicateEmail setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public CheckForDuplicateEmail set(String parameterName, Object value) {
      return (CheckForDuplicateEmail) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "checkForDuplicateUsername".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link CheckForDuplicateUsername#execute()} method to invoke the
   * remote operation.
   *
   * @param username
   * @return the request
   */
  public CheckForDuplicateUsername checkForDuplicateUsername(java.lang.String username) throws java.io.IOException {
    CheckForDuplicateUsername result = new CheckForDuplicateUsername(username);
    initialize(result);
    return result;
  }

  public class CheckForDuplicateUsername extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "checkForDuplicateUsername/{username}";

    /**
     * Create a request for the method "checkForDuplicateUsername".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link CheckForDuplicateUsername#execute()} method to invoke the
     * remote operation. <p> {@link CheckForDuplicateUsername#initialize(com.google.api.client.googlea
     * pis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param username
     * @since 1.13
     */
    protected CheckForDuplicateUsername(java.lang.String username) {
      super(Poliuserendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
      this.username = com.google.api.client.util.Preconditions.checkNotNull(username, "Required parameter username must be specified.");
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
    public CheckForDuplicateUsername setAlt(java.lang.String alt) {
      return (CheckForDuplicateUsername) super.setAlt(alt);
    }

    @Override
    public CheckForDuplicateUsername setFields(java.lang.String fields) {
      return (CheckForDuplicateUsername) super.setFields(fields);
    }

    @Override
    public CheckForDuplicateUsername setKey(java.lang.String key) {
      return (CheckForDuplicateUsername) super.setKey(key);
    }

    @Override
    public CheckForDuplicateUsername setOauthToken(java.lang.String oauthToken) {
      return (CheckForDuplicateUsername) super.setOauthToken(oauthToken);
    }

    @Override
    public CheckForDuplicateUsername setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CheckForDuplicateUsername) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CheckForDuplicateUsername setQuotaUser(java.lang.String quotaUser) {
      return (CheckForDuplicateUsername) super.setQuotaUser(quotaUser);
    }

    @Override
    public CheckForDuplicateUsername setUserIp(java.lang.String userIp) {
      return (CheckForDuplicateUsername) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String username;

    /**

     */
    public java.lang.String getUsername() {
      return username;
    }

    public CheckForDuplicateUsername setUsername(java.lang.String username) {
      this.username = username;
      return this;
    }

    @Override
    public CheckForDuplicateUsername set(String parameterName, Object value) {
      return (CheckForDuplicateUsername) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getPoliUser".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link GetPoliUser#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetPoliUser getPoliUser(java.lang.Long id) throws java.io.IOException {
    GetPoliUser result = new GetPoliUser(id);
    initialize(result);
    return result;
  }

  public class GetPoliUser extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "poliuser/{id}";

    /**
     * Create a request for the method "getPoliUser".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link GetPoliUser#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetPoliUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetPoliUser(java.lang.Long id) {
      super(Poliuserendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
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
    public GetPoliUser setAlt(java.lang.String alt) {
      return (GetPoliUser) super.setAlt(alt);
    }

    @Override
    public GetPoliUser setFields(java.lang.String fields) {
      return (GetPoliUser) super.setFields(fields);
    }

    @Override
    public GetPoliUser setKey(java.lang.String key) {
      return (GetPoliUser) super.setKey(key);
    }

    @Override
    public GetPoliUser setOauthToken(java.lang.String oauthToken) {
      return (GetPoliUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetPoliUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetPoliUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetPoliUser setQuotaUser(java.lang.String quotaUser) {
      return (GetPoliUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetPoliUser setUserIp(java.lang.String userIp) {
      return (GetPoliUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetPoliUser setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetPoliUser set(String parameterName, Object value) {
      return (GetPoliUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertPoliUser".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link InsertPoliUser#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser}
   * @return the request
   */
  public InsertPoliUser insertPoliUser(it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser content) throws java.io.IOException {
    InsertPoliUser result = new InsertPoliUser(content);
    initialize(result);
    return result;
  }

  public class InsertPoliUser extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "poliuser";

    /**
     * Create a request for the method "insertPoliUser".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link InsertPoliUser#execute()} method to invoke the remote
     * operation. <p> {@link InsertPoliUser#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser}
     * @since 1.13
     */
    protected InsertPoliUser(it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser content) {
      super(Poliuserendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
    }

    @Override
    public InsertPoliUser setAlt(java.lang.String alt) {
      return (InsertPoliUser) super.setAlt(alt);
    }

    @Override
    public InsertPoliUser setFields(java.lang.String fields) {
      return (InsertPoliUser) super.setFields(fields);
    }

    @Override
    public InsertPoliUser setKey(java.lang.String key) {
      return (InsertPoliUser) super.setKey(key);
    }

    @Override
    public InsertPoliUser setOauthToken(java.lang.String oauthToken) {
      return (InsertPoliUser) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertPoliUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertPoliUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertPoliUser setQuotaUser(java.lang.String quotaUser) {
      return (InsertPoliUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertPoliUser setUserIp(java.lang.String userIp) {
      return (InsertPoliUser) super.setUserIp(userIp);
    }

    @Override
    public InsertPoliUser set(String parameterName, Object value) {
      return (InsertPoliUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listPoliUser".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link ListPoliUser#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListPoliUser listPoliUser() throws java.io.IOException {
    ListPoliUser result = new ListPoliUser();
    initialize(result);
    return result;
  }

  public class ListPoliUser extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponsePoliUser> {

    private static final String REST_PATH = "poliuser";

    /**
     * Create a request for the method "listPoliUser".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link ListPoliUser#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListPoliUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListPoliUser() {
      super(Poliuserendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponsePoliUser.class);
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
    public ListPoliUser setAlt(java.lang.String alt) {
      return (ListPoliUser) super.setAlt(alt);
    }

    @Override
    public ListPoliUser setFields(java.lang.String fields) {
      return (ListPoliUser) super.setFields(fields);
    }

    @Override
    public ListPoliUser setKey(java.lang.String key) {
      return (ListPoliUser) super.setKey(key);
    }

    @Override
    public ListPoliUser setOauthToken(java.lang.String oauthToken) {
      return (ListPoliUser) super.setOauthToken(oauthToken);
    }

    @Override
    public ListPoliUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListPoliUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListPoliUser setQuotaUser(java.lang.String quotaUser) {
      return (ListPoliUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListPoliUser setUserIp(java.lang.String userIp) {
      return (ListPoliUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListPoliUser setCursor(java.lang.String cursor) {
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

    public ListPoliUser setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListPoliUser set(String parameterName, Object value) {
      return (ListPoliUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "prova".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link Prova#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public Prova prova() throws java.io.IOException {
    Prova result = new Prova();
    initialize(result);
    return result;
  }

  public class Prova extends PoliuserendpointRequest<Void> {

    private static final String REST_PATH = "prova";

    /**
     * Create a request for the method "prova".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link Prova#execute()} method to invoke the remote operation.
     * <p> {@link
     * Prova#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected Prova() {
      super(Poliuserendpoint.this, "POST", REST_PATH, null, Void.class);
    }

    @Override
    public Prova setAlt(java.lang.String alt) {
      return (Prova) super.setAlt(alt);
    }

    @Override
    public Prova setFields(java.lang.String fields) {
      return (Prova) super.setFields(fields);
    }

    @Override
    public Prova setKey(java.lang.String key) {
      return (Prova) super.setKey(key);
    }

    @Override
    public Prova setOauthToken(java.lang.String oauthToken) {
      return (Prova) super.setOauthToken(oauthToken);
    }

    @Override
    public Prova setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Prova) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Prova setQuotaUser(java.lang.String quotaUser) {
      return (Prova) super.setQuotaUser(quotaUser);
    }

    @Override
    public Prova setUserIp(java.lang.String userIp) {
      return (Prova) super.setUserIp(userIp);
    }

    @Override
    public Prova set(String parameterName, Object value) {
      return (Prova) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removePoliUser".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link RemovePoliUser#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemovePoliUser removePoliUser(java.lang.Long id) throws java.io.IOException {
    RemovePoliUser result = new RemovePoliUser(id);
    initialize(result);
    return result;
  }

  public class RemovePoliUser extends PoliuserendpointRequest<Void> {

    private static final String REST_PATH = "poliuser/{id}";

    /**
     * Create a request for the method "removePoliUser".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link RemovePoliUser#execute()} method to invoke the remote
     * operation. <p> {@link RemovePoliUser#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemovePoliUser(java.lang.Long id) {
      super(Poliuserendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemovePoliUser setAlt(java.lang.String alt) {
      return (RemovePoliUser) super.setAlt(alt);
    }

    @Override
    public RemovePoliUser setFields(java.lang.String fields) {
      return (RemovePoliUser) super.setFields(fields);
    }

    @Override
    public RemovePoliUser setKey(java.lang.String key) {
      return (RemovePoliUser) super.setKey(key);
    }

    @Override
    public RemovePoliUser setOauthToken(java.lang.String oauthToken) {
      return (RemovePoliUser) super.setOauthToken(oauthToken);
    }

    @Override
    public RemovePoliUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemovePoliUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemovePoliUser setQuotaUser(java.lang.String quotaUser) {
      return (RemovePoliUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemovePoliUser setUserIp(java.lang.String userIp) {
      return (RemovePoliUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemovePoliUser setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemovePoliUser set(String parameterName, Object value) {
      return (RemovePoliUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updatePoliUser".
   *
   * This request holds the parameters needed by the poliuserendpoint server.  After setting any
   * optional parameters, call the {@link UpdatePoliUser#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser}
   * @return the request
   */
  public UpdatePoliUser updatePoliUser(it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser content) throws java.io.IOException {
    UpdatePoliUser result = new UpdatePoliUser(content);
    initialize(result);
    return result;
  }

  public class UpdatePoliUser extends PoliuserendpointRequest<it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser> {

    private static final String REST_PATH = "poliuser";

    /**
     * Create a request for the method "updatePoliUser".
     *
     * This request holds the parameters needed by the the poliuserendpoint server.  After setting any
     * optional parameters, call the {@link UpdatePoliUser#execute()} method to invoke the remote
     * operation. <p> {@link UpdatePoliUser#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser}
     * @since 1.13
     */
    protected UpdatePoliUser(it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser content) {
      super(Poliuserendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser.class);
    }

    @Override
    public UpdatePoliUser setAlt(java.lang.String alt) {
      return (UpdatePoliUser) super.setAlt(alt);
    }

    @Override
    public UpdatePoliUser setFields(java.lang.String fields) {
      return (UpdatePoliUser) super.setFields(fields);
    }

    @Override
    public UpdatePoliUser setKey(java.lang.String key) {
      return (UpdatePoliUser) super.setKey(key);
    }

    @Override
    public UpdatePoliUser setOauthToken(java.lang.String oauthToken) {
      return (UpdatePoliUser) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdatePoliUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdatePoliUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdatePoliUser setQuotaUser(java.lang.String quotaUser) {
      return (UpdatePoliUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdatePoliUser setUserIp(java.lang.String userIp) {
      return (UpdatePoliUser) super.setUserIp(userIp);
    }

    @Override
    public UpdatePoliUser set(String parameterName, Object value) {
      return (UpdatePoliUser) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Poliuserendpoint}.
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

    /** Builds a new instance of {@link Poliuserendpoint}. */
    @Override
    public Poliuserendpoint build() {
      return new Poliuserendpoint(this);
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
     * Set the {@link PoliuserendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPoliuserendpointRequestInitializer(
        PoliuserendpointRequestInitializer poliuserendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(poliuserendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
