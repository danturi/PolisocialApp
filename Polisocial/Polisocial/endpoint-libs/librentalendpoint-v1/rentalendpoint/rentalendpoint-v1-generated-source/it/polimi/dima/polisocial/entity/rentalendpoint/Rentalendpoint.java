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
 * on 2014-07-27 at 02:22:17 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.rentalendpoint;

/**
 * Service definition for Rentalendpoint (v1).
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
 * This service uses {@link RentalendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Rentalendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the rentalendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "rentalendpoint/v1/";

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
  public Rentalendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Rentalendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getRental".
   *
   * This request holds the parameters needed by the rentalendpoint server.  After setting any
   * optional parameters, call the {@link GetRental#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetRental getRental(java.lang.Long id) throws java.io.IOException {
    GetRental result = new GetRental(id);
    initialize(result);
    return result;
  }

  public class GetRental extends RentalendpointRequest<it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental> {

    private static final String REST_PATH = "rental/{id}";

    /**
     * Create a request for the method "getRental".
     *
     * This request holds the parameters needed by the the rentalendpoint server.  After setting any
     * optional parameters, call the {@link GetRental#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetRental#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetRental(java.lang.Long id) {
      super(Rentalendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental.class);
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
    public GetRental setAlt(java.lang.String alt) {
      return (GetRental) super.setAlt(alt);
    }

    @Override
    public GetRental setFields(java.lang.String fields) {
      return (GetRental) super.setFields(fields);
    }

    @Override
    public GetRental setKey(java.lang.String key) {
      return (GetRental) super.setKey(key);
    }

    @Override
    public GetRental setOauthToken(java.lang.String oauthToken) {
      return (GetRental) super.setOauthToken(oauthToken);
    }

    @Override
    public GetRental setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetRental) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetRental setQuotaUser(java.lang.String quotaUser) {
      return (GetRental) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetRental setUserIp(java.lang.String userIp) {
      return (GetRental) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetRental setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetRental set(String parameterName, Object value) {
      return (GetRental) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertRental".
   *
   * This request holds the parameters needed by the rentalendpoint server.  After setting any
   * optional parameters, call the {@link InsertRental#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental}
   * @return the request
   */
  public InsertRental insertRental(it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental content) throws java.io.IOException {
    InsertRental result = new InsertRental(content);
    initialize(result);
    return result;
  }

  public class InsertRental extends RentalendpointRequest<it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental> {

    private static final String REST_PATH = "rental";

    /**
     * Create a request for the method "insertRental".
     *
     * This request holds the parameters needed by the the rentalendpoint server.  After setting any
     * optional parameters, call the {@link InsertRental#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertRental#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental}
     * @since 1.13
     */
    protected InsertRental(it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental content) {
      super(Rentalendpoint.this, "POST", REST_PATH, content, it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental.class);
    }

    @Override
    public InsertRental setAlt(java.lang.String alt) {
      return (InsertRental) super.setAlt(alt);
    }

    @Override
    public InsertRental setFields(java.lang.String fields) {
      return (InsertRental) super.setFields(fields);
    }

    @Override
    public InsertRental setKey(java.lang.String key) {
      return (InsertRental) super.setKey(key);
    }

    @Override
    public InsertRental setOauthToken(java.lang.String oauthToken) {
      return (InsertRental) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertRental setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertRental) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertRental setQuotaUser(java.lang.String quotaUser) {
      return (InsertRental) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertRental setUserIp(java.lang.String userIp) {
      return (InsertRental) super.setUserIp(userIp);
    }

    @Override
    public InsertRental set(String parameterName, Object value) {
      return (InsertRental) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listRental".
   *
   * This request holds the parameters needed by the rentalendpoint server.  After setting any
   * optional parameters, call the {@link ListRental#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListRental listRental() throws java.io.IOException {
    ListRental result = new ListRental();
    initialize(result);
    return result;
  }

  public class ListRental extends RentalendpointRequest<it.polimi.dima.polisocial.entity.rentalendpoint.model.CollectionResponseRental> {

    private static final String REST_PATH = "rental";

    /**
     * Create a request for the method "listRental".
     *
     * This request holds the parameters needed by the the rentalendpoint server.  After setting any
     * optional parameters, call the {@link ListRental#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListRental#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListRental() {
      super(Rentalendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.entity.rentalendpoint.model.CollectionResponseRental.class);
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
    public ListRental setAlt(java.lang.String alt) {
      return (ListRental) super.setAlt(alt);
    }

    @Override
    public ListRental setFields(java.lang.String fields) {
      return (ListRental) super.setFields(fields);
    }

    @Override
    public ListRental setKey(java.lang.String key) {
      return (ListRental) super.setKey(key);
    }

    @Override
    public ListRental setOauthToken(java.lang.String oauthToken) {
      return (ListRental) super.setOauthToken(oauthToken);
    }

    @Override
    public ListRental setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListRental) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListRental setQuotaUser(java.lang.String quotaUser) {
      return (ListRental) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListRental setUserIp(java.lang.String userIp) {
      return (ListRental) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListRental setCursor(java.lang.String cursor) {
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

    public ListRental setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListRental set(String parameterName, Object value) {
      return (ListRental) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeRental".
   *
   * This request holds the parameters needed by the rentalendpoint server.  After setting any
   * optional parameters, call the {@link RemoveRental#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveRental removeRental(java.lang.Long id) throws java.io.IOException {
    RemoveRental result = new RemoveRental(id);
    initialize(result);
    return result;
  }

  public class RemoveRental extends RentalendpointRequest<Void> {

    private static final String REST_PATH = "rental/{id}";

    /**
     * Create a request for the method "removeRental".
     *
     * This request holds the parameters needed by the the rentalendpoint server.  After setting any
     * optional parameters, call the {@link RemoveRental#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveRental#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveRental(java.lang.Long id) {
      super(Rentalendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveRental setAlt(java.lang.String alt) {
      return (RemoveRental) super.setAlt(alt);
    }

    @Override
    public RemoveRental setFields(java.lang.String fields) {
      return (RemoveRental) super.setFields(fields);
    }

    @Override
    public RemoveRental setKey(java.lang.String key) {
      return (RemoveRental) super.setKey(key);
    }

    @Override
    public RemoveRental setOauthToken(java.lang.String oauthToken) {
      return (RemoveRental) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveRental setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveRental) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveRental setQuotaUser(java.lang.String quotaUser) {
      return (RemoveRental) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveRental setUserIp(java.lang.String userIp) {
      return (RemoveRental) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveRental setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveRental set(String parameterName, Object value) {
      return (RemoveRental) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateRental".
   *
   * This request holds the parameters needed by the rentalendpoint server.  After setting any
   * optional parameters, call the {@link UpdateRental#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental}
   * @return the request
   */
  public UpdateRental updateRental(it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental content) throws java.io.IOException {
    UpdateRental result = new UpdateRental(content);
    initialize(result);
    return result;
  }

  public class UpdateRental extends RentalendpointRequest<it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental> {

    private static final String REST_PATH = "rental";

    /**
     * Create a request for the method "updateRental".
     *
     * This request holds the parameters needed by the the rentalendpoint server.  After setting any
     * optional parameters, call the {@link UpdateRental#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateRental#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental}
     * @since 1.13
     */
    protected UpdateRental(it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental content) {
      super(Rentalendpoint.this, "PUT", REST_PATH, content, it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental.class);
    }

    @Override
    public UpdateRental setAlt(java.lang.String alt) {
      return (UpdateRental) super.setAlt(alt);
    }

    @Override
    public UpdateRental setFields(java.lang.String fields) {
      return (UpdateRental) super.setFields(fields);
    }

    @Override
    public UpdateRental setKey(java.lang.String key) {
      return (UpdateRental) super.setKey(key);
    }

    @Override
    public UpdateRental setOauthToken(java.lang.String oauthToken) {
      return (UpdateRental) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateRental setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateRental) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateRental setQuotaUser(java.lang.String quotaUser) {
      return (UpdateRental) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateRental setUserIp(java.lang.String userIp) {
      return (UpdateRental) super.setUserIp(userIp);
    }

    @Override
    public UpdateRental set(String parameterName, Object value) {
      return (UpdateRental) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Rentalendpoint}.
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

    /** Builds a new instance of {@link Rentalendpoint}. */
    @Override
    public Rentalendpoint build() {
      return new Rentalendpoint(this);
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
     * Set the {@link RentalendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRentalendpointRequestInitializer(
        RentalendpointRequestInitializer rentalendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(rentalendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
