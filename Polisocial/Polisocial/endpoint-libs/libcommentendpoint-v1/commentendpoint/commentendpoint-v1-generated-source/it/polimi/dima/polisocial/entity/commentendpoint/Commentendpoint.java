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
 * on 2014-07-31 at 15:47:10 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.commentendpoint;

/**
 * Service definition for Commentendpoint (v1).
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
 * This service uses {@link CommentendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Commentendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the commentendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "commentendpoint/v1/";

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
  public Commentendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Commentendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getComment".
   *
   * This request holds the parameters needed by the commentendpoint server.  After setting any
   * optional parameters, call the {@link GetComment#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetComment getComment(java.lang.Long id) throws java.io.IOException {
    GetComment result = new GetComment(id);
    initialize(result);
    return result;
  }

  public class GetComment extends CommentendpointRequest<it.polimi.dima.polisocial.entity.commentendpoint.model.Comment> {

    private static final String REST_PATH = "comment/{id}";

    /**
     * Create a request for the method "getComment".
     *
     * This request holds the parameters needed by the the commentendpoint server.  After setting any
     * optional parameters, call the {@link GetComment#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetComment#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetComment(java.lang.Long id) {
      super(Commentendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.commentendpoint.model.Comment.class);
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
    public GetComment setAlt(java.lang.String alt) {
      return (GetComment) super.setAlt(alt);
    }

    @Override
    public GetComment setFields(java.lang.String fields) {
      return (GetComment) super.setFields(fields);
    }

    @Override
    public GetComment setKey(java.lang.String key) {
      return (GetComment) super.setKey(key);
    }

    @Override
    public GetComment setOauthToken(java.lang.String oauthToken) {
      return (GetComment) super.setOauthToken(oauthToken);
    }

    @Override
    public GetComment setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetComment) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetComment setQuotaUser(java.lang.String quotaUser) {
      return (GetComment) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetComment setUserIp(java.lang.String userIp) {
      return (GetComment) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetComment setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetComment set(String parameterName, Object value) {
      return (GetComment) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertComment".
   *
   * This request holds the parameters needed by the commentendpoint server.  After setting any
   * optional parameters, call the {@link InsertComment#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.commentendpoint.model.Comment}
   * @return the request
   */
  public InsertComment insertComment(it.polimi.dima.polisocial.entity.commentendpoint.model.Comment content) throws java.io.IOException {
    InsertComment result = new InsertComment(content);
    initialize(result);
    return result;
  }

  public class InsertComment extends CommentendpointRequest<it.polimi.dima.polisocial.entity.commentendpoint.model.Comment> {

    private static final String REST_PATH = "comment";

    /**
     * Create a request for the method "insertComment".
     *
     * This request holds the parameters needed by the the commentendpoint server.  After setting any
     * optional parameters, call the {@link InsertComment#execute()} method to invoke the remote
     * operation. <p> {@link InsertComment#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.commentendpoint.model.Comment}
     * @since 1.13
     */
    protected InsertComment(it.polimi.dima.polisocial.entity.commentendpoint.model.Comment content) {
      super(Commentendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.commentendpoint.model.Comment.class);
    }

    @Override
    public InsertComment setAlt(java.lang.String alt) {
      return (InsertComment) super.setAlt(alt);
    }

    @Override
    public InsertComment setFields(java.lang.String fields) {
      return (InsertComment) super.setFields(fields);
    }

    @Override
    public InsertComment setKey(java.lang.String key) {
      return (InsertComment) super.setKey(key);
    }

    @Override
    public InsertComment setOauthToken(java.lang.String oauthToken) {
      return (InsertComment) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertComment setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertComment) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertComment setQuotaUser(java.lang.String quotaUser) {
      return (InsertComment) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertComment setUserIp(java.lang.String userIp) {
      return (InsertComment) super.setUserIp(userIp);
    }

    @Override
    public InsertComment set(String parameterName, Object value) {
      return (InsertComment) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listComment".
   *
   * This request holds the parameters needed by the commentendpoint server.  After setting any
   * optional parameters, call the {@link ListComment#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListComment listComment() throws java.io.IOException {
    ListComment result = new ListComment();
    initialize(result);
    return result;
  }

  public class ListComment extends CommentendpointRequest<it.polimi.dima.polisocial.entity.commentendpoint.model.CollectionResponseComment> {

    private static final String REST_PATH = "comment";

    /**
     * Create a request for the method "listComment".
     *
     * This request holds the parameters needed by the the commentendpoint server.  After setting any
     * optional parameters, call the {@link ListComment#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListComment#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListComment() {
      super(Commentendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.commentendpoint.model.CollectionResponseComment.class);
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
    public ListComment setAlt(java.lang.String alt) {
      return (ListComment) super.setAlt(alt);
    }

    @Override
    public ListComment setFields(java.lang.String fields) {
      return (ListComment) super.setFields(fields);
    }

    @Override
    public ListComment setKey(java.lang.String key) {
      return (ListComment) super.setKey(key);
    }

    @Override
    public ListComment setOauthToken(java.lang.String oauthToken) {
      return (ListComment) super.setOauthToken(oauthToken);
    }

    @Override
    public ListComment setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListComment) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListComment setQuotaUser(java.lang.String quotaUser) {
      return (ListComment) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListComment setUserIp(java.lang.String userIp) {
      return (ListComment) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListComment setCursor(java.lang.String cursor) {
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

    public ListComment setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListComment set(String parameterName, Object value) {
      return (ListComment) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeComment".
   *
   * This request holds the parameters needed by the commentendpoint server.  After setting any
   * optional parameters, call the {@link RemoveComment#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveComment removeComment(java.lang.Long id) throws java.io.IOException {
    RemoveComment result = new RemoveComment(id);
    initialize(result);
    return result;
  }

  public class RemoveComment extends CommentendpointRequest<Void> {

    private static final String REST_PATH = "comment/{id}";

    /**
     * Create a request for the method "removeComment".
     *
     * This request holds the parameters needed by the the commentendpoint server.  After setting any
     * optional parameters, call the {@link RemoveComment#execute()} method to invoke the remote
     * operation. <p> {@link RemoveComment#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveComment(java.lang.Long id) {
      super(Commentendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveComment setAlt(java.lang.String alt) {
      return (RemoveComment) super.setAlt(alt);
    }

    @Override
    public RemoveComment setFields(java.lang.String fields) {
      return (RemoveComment) super.setFields(fields);
    }

    @Override
    public RemoveComment setKey(java.lang.String key) {
      return (RemoveComment) super.setKey(key);
    }

    @Override
    public RemoveComment setOauthToken(java.lang.String oauthToken) {
      return (RemoveComment) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveComment setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveComment) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveComment setQuotaUser(java.lang.String quotaUser) {
      return (RemoveComment) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveComment setUserIp(java.lang.String userIp) {
      return (RemoveComment) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveComment setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveComment set(String parameterName, Object value) {
      return (RemoveComment) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateComment".
   *
   * This request holds the parameters needed by the commentendpoint server.  After setting any
   * optional parameters, call the {@link UpdateComment#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.commentendpoint.model.Comment}
   * @return the request
   */
  public UpdateComment updateComment(it.polimi.dima.polisocial.entity.commentendpoint.model.Comment content) throws java.io.IOException {
    UpdateComment result = new UpdateComment(content);
    initialize(result);
    return result;
  }

  public class UpdateComment extends CommentendpointRequest<it.polimi.dima.polisocial.entity.commentendpoint.model.Comment> {

    private static final String REST_PATH = "comment";

    /**
     * Create a request for the method "updateComment".
     *
     * This request holds the parameters needed by the the commentendpoint server.  After setting any
     * optional parameters, call the {@link UpdateComment#execute()} method to invoke the remote
     * operation. <p> {@link UpdateComment#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.commentendpoint.model.Comment}
     * @since 1.13
     */
    protected UpdateComment(it.polimi.dima.polisocial.entity.commentendpoint.model.Comment content) {
      super(Commentendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.commentendpoint.model.Comment.class);
    }

    @Override
    public UpdateComment setAlt(java.lang.String alt) {
      return (UpdateComment) super.setAlt(alt);
    }

    @Override
    public UpdateComment setFields(java.lang.String fields) {
      return (UpdateComment) super.setFields(fields);
    }

    @Override
    public UpdateComment setKey(java.lang.String key) {
      return (UpdateComment) super.setKey(key);
    }

    @Override
    public UpdateComment setOauthToken(java.lang.String oauthToken) {
      return (UpdateComment) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateComment setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateComment) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateComment setQuotaUser(java.lang.String quotaUser) {
      return (UpdateComment) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateComment setUserIp(java.lang.String userIp) {
      return (UpdateComment) super.setUserIp(userIp);
    }

    @Override
    public UpdateComment set(String parameterName, Object value) {
      return (UpdateComment) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Commentendpoint}.
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

    /** Builds a new instance of {@link Commentendpoint}. */
    @Override
    public Commentendpoint build() {
      return new Commentendpoint(this);
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
     * Set the {@link CommentendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setCommentendpointRequestInitializer(
        CommentendpointRequestInitializer commentendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(commentendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}