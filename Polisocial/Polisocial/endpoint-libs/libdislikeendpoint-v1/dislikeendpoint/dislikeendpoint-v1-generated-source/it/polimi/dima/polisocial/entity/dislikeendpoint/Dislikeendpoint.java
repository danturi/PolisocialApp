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
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-11-25 at 02:26:45 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.dislikeendpoint;

/**
 * Service definition for Dislikeendpoint (v1).
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
 * This service uses {@link DislikeendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Dislikeendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the dislikeendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "dislikeendpoint/v1/";

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
  public Dislikeendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Dislikeendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link GetDisLike#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetDisLike getDisLike(java.lang.Long id) throws java.io.IOException {
    GetDisLike result = new GetDisLike(id);
    initialize(result);
    return result;
  }

  public class GetDisLike extends DislikeendpointRequest<it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike> {

    private static final String REST_PATH = "dislike/{id}";

    /**
     * Create a request for the method "getDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link GetDisLike#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetDisLike#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetDisLike(java.lang.Long id) {
      super(Dislikeendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike.class);
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
    public GetDisLike setAlt(java.lang.String alt) {
      return (GetDisLike) super.setAlt(alt);
    }

    @Override
    public GetDisLike setFields(java.lang.String fields) {
      return (GetDisLike) super.setFields(fields);
    }

    @Override
    public GetDisLike setKey(java.lang.String key) {
      return (GetDisLike) super.setKey(key);
    }

    @Override
    public GetDisLike setOauthToken(java.lang.String oauthToken) {
      return (GetDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public GetDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetDisLike setQuotaUser(java.lang.String quotaUser) {
      return (GetDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetDisLike setUserIp(java.lang.String userIp) {
      return (GetDisLike) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetDisLike setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetDisLike set(String parameterName, Object value) {
      return (GetDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getPostDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link GetPostDisLike#execute()} method to invoke the remote
   * operation.
   *
   * @param postId
   * @return the request
   */
  public GetPostDisLike getPostDisLike(java.lang.Long postId) throws java.io.IOException {
    GetPostDisLike result = new GetPostDisLike(postId);
    initialize(result);
    return result;
  }

  public class GetPostDisLike extends DislikeendpointRequest<it.polimi.dima.polisocial.entity.dislikeendpoint.model.ResponseObject> {

    private static final String REST_PATH = "responseobject/{postId}";

    /**
     * Create a request for the method "getPostDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link GetPostDisLike#execute()} method to invoke the remote
     * operation. <p> {@link GetPostDisLike#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param postId
     * @since 1.13
     */
    protected GetPostDisLike(java.lang.Long postId) {
      super(Dislikeendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.dislikeendpoint.model.ResponseObject.class);
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
    public GetPostDisLike setAlt(java.lang.String alt) {
      return (GetPostDisLike) super.setAlt(alt);
    }

    @Override
    public GetPostDisLike setFields(java.lang.String fields) {
      return (GetPostDisLike) super.setFields(fields);
    }

    @Override
    public GetPostDisLike setKey(java.lang.String key) {
      return (GetPostDisLike) super.setKey(key);
    }

    @Override
    public GetPostDisLike setOauthToken(java.lang.String oauthToken) {
      return (GetPostDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public GetPostDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetPostDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetPostDisLike setQuotaUser(java.lang.String quotaUser) {
      return (GetPostDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetPostDisLike setUserIp(java.lang.String userIp) {
      return (GetPostDisLike) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long postId;

    /**

     */
    public java.lang.Long getPostId() {
      return postId;
    }

    public GetPostDisLike setPostId(java.lang.Long postId) {
      this.postId = postId;
      return this;
    }

    @Override
    public GetPostDisLike set(String parameterName, Object value) {
      return (GetPostDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link InsertDisLike#execute()} method to invoke the remote
   * operation.
   *
   * @param postType
   * @param content the {@link it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike}
   * @return the request
   */
  public InsertDisLike insertDisLike(java.lang.String postType, it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike content) throws java.io.IOException {
    InsertDisLike result = new InsertDisLike(postType, content);
    initialize(result);
    return result;
  }

  public class InsertDisLike extends DislikeendpointRequest<it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike> {

    private static final String REST_PATH = "dislike/{postType}";

    /**
     * Create a request for the method "insertDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link InsertDisLike#execute()} method to invoke the remote
     * operation. <p> {@link InsertDisLike#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param postType
     * @param content the {@link it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike}
     * @since 1.13
     */
    protected InsertDisLike(java.lang.String postType, it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike content) {
      super(Dislikeendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike.class);
      this.postType = com.google.api.client.util.Preconditions.checkNotNull(postType, "Required parameter postType must be specified.");
    }

    @Override
    public InsertDisLike setAlt(java.lang.String alt) {
      return (InsertDisLike) super.setAlt(alt);
    }

    @Override
    public InsertDisLike setFields(java.lang.String fields) {
      return (InsertDisLike) super.setFields(fields);
    }

    @Override
    public InsertDisLike setKey(java.lang.String key) {
      return (InsertDisLike) super.setKey(key);
    }

    @Override
    public InsertDisLike setOauthToken(java.lang.String oauthToken) {
      return (InsertDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertDisLike setQuotaUser(java.lang.String quotaUser) {
      return (InsertDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertDisLike setUserIp(java.lang.String userIp) {
      return (InsertDisLike) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String postType;

    /**

     */
    public java.lang.String getPostType() {
      return postType;
    }

    public InsertDisLike setPostType(java.lang.String postType) {
      this.postType = postType;
      return this;
    }

    @Override
    public InsertDisLike set(String parameterName, Object value) {
      return (InsertDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link ListDisLike#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListDisLike listDisLike() throws java.io.IOException {
    ListDisLike result = new ListDisLike();
    initialize(result);
    return result;
  }

  public class ListDisLike extends DislikeendpointRequest<it.polimi.dima.polisocial.entity.dislikeendpoint.model.CollectionResponseDisLike> {

    private static final String REST_PATH = "dislike";

    /**
     * Create a request for the method "listDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link ListDisLike#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListDisLike#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListDisLike() {
      super(Dislikeendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.dislikeendpoint.model.CollectionResponseDisLike.class);
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
    public ListDisLike setAlt(java.lang.String alt) {
      return (ListDisLike) super.setAlt(alt);
    }

    @Override
    public ListDisLike setFields(java.lang.String fields) {
      return (ListDisLike) super.setFields(fields);
    }

    @Override
    public ListDisLike setKey(java.lang.String key) {
      return (ListDisLike) super.setKey(key);
    }

    @Override
    public ListDisLike setOauthToken(java.lang.String oauthToken) {
      return (ListDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public ListDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListDisLike setQuotaUser(java.lang.String quotaUser) {
      return (ListDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListDisLike setUserIp(java.lang.String userIp) {
      return (ListDisLike) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListDisLike setCursor(java.lang.String cursor) {
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

    public ListDisLike setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListDisLike set(String parameterName, Object value) {
      return (ListDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link RemoveDisLike#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveDisLike removeDisLike(java.lang.Long id) throws java.io.IOException {
    RemoveDisLike result = new RemoveDisLike(id);
    initialize(result);
    return result;
  }

  public class RemoveDisLike extends DislikeendpointRequest<Void> {

    private static final String REST_PATH = "dislike/{id}";

    /**
     * Create a request for the method "removeDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link RemoveDisLike#execute()} method to invoke the remote
     * operation. <p> {@link RemoveDisLike#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveDisLike(java.lang.Long id) {
      super(Dislikeendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveDisLike setAlt(java.lang.String alt) {
      return (RemoveDisLike) super.setAlt(alt);
    }

    @Override
    public RemoveDisLike setFields(java.lang.String fields) {
      return (RemoveDisLike) super.setFields(fields);
    }

    @Override
    public RemoveDisLike setKey(java.lang.String key) {
      return (RemoveDisLike) super.setKey(key);
    }

    @Override
    public RemoveDisLike setOauthToken(java.lang.String oauthToken) {
      return (RemoveDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveDisLike setQuotaUser(java.lang.String quotaUser) {
      return (RemoveDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveDisLike setUserIp(java.lang.String userIp) {
      return (RemoveDisLike) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveDisLike setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveDisLike set(String parameterName, Object value) {
      return (RemoveDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateDisLike".
   *
   * This request holds the parameters needed by the dislikeendpoint server.  After setting any
   * optional parameters, call the {@link UpdateDisLike#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike}
   * @return the request
   */
  public UpdateDisLike updateDisLike(it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike content) throws java.io.IOException {
    UpdateDisLike result = new UpdateDisLike(content);
    initialize(result);
    return result;
  }

  public class UpdateDisLike extends DislikeendpointRequest<it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike> {

    private static final String REST_PATH = "dislike";

    /**
     * Create a request for the method "updateDisLike".
     *
     * This request holds the parameters needed by the the dislikeendpoint server.  After setting any
     * optional parameters, call the {@link UpdateDisLike#execute()} method to invoke the remote
     * operation. <p> {@link UpdateDisLike#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike}
     * @since 1.13
     */
    protected UpdateDisLike(it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike content) {
      super(Dislikeendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.dislikeendpoint.model.DisLike.class);
    }

    @Override
    public UpdateDisLike setAlt(java.lang.String alt) {
      return (UpdateDisLike) super.setAlt(alt);
    }

    @Override
    public UpdateDisLike setFields(java.lang.String fields) {
      return (UpdateDisLike) super.setFields(fields);
    }

    @Override
    public UpdateDisLike setKey(java.lang.String key) {
      return (UpdateDisLike) super.setKey(key);
    }

    @Override
    public UpdateDisLike setOauthToken(java.lang.String oauthToken) {
      return (UpdateDisLike) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateDisLike setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateDisLike) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateDisLike setQuotaUser(java.lang.String quotaUser) {
      return (UpdateDisLike) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateDisLike setUserIp(java.lang.String userIp) {
      return (UpdateDisLike) super.setUserIp(userIp);
    }

    @Override
    public UpdateDisLike set(String parameterName, Object value) {
      return (UpdateDisLike) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Dislikeendpoint}.
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

    /** Builds a new instance of {@link Dislikeendpoint}. */
    @Override
    public Dislikeendpoint build() {
      return new Dislikeendpoint(this);
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
     * Set the {@link DislikeendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setDislikeendpointRequestInitializer(
        DislikeendpointRequestInitializer dislikeendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(dislikeendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
