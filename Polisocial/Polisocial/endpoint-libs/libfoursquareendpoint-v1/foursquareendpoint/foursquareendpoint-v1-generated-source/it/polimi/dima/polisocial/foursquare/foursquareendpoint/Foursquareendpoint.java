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
 * on 2014-11-25 at 02:28:09 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.foursquare.foursquareendpoint;

/**
 * Service definition for Foursquareendpoint (v1).
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
 * This service uses {@link FoursquareendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Foursquareendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the foursquareendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "foursquareendpoint/v1/";

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
  public Foursquareendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Foursquareendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "addVenueGPSInfo".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link AddVenueGPSInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param userId
   * @param name
   * @param coordinates
   * @param categoryId
   * @return the request
   */
  public AddVenueGPSInfo addVenueGPSInfo(java.lang.Long userId, java.lang.String name, java.lang.String coordinates, java.lang.String categoryId) throws java.io.IOException {
    AddVenueGPSInfo result = new AddVenueGPSInfo(userId, name, coordinates, categoryId);
    initialize(result);
    return result;
  }

  public class AddVenueGPSInfo extends FoursquareendpointRequest<Void> {

    private static final String REST_PATH = "addVenueGPSInfo/{userId}/{name}/{coordinates}/{categoryId}";

    /**
     * Create a request for the method "addVenueGPSInfo".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link AddVenueGPSInfo#execute()} method to invoke the remote
     * operation. <p> {@link AddVenueGPSInfo#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param userId
     * @param name
     * @param coordinates
     * @param categoryId
     * @since 1.13
     */
    protected AddVenueGPSInfo(java.lang.Long userId, java.lang.String name, java.lang.String coordinates, java.lang.String categoryId) {
      super(Foursquareendpoint.this, "POST", REST_PATH, null, Void.class);
      this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
      this.coordinates = com.google.api.client.util.Preconditions.checkNotNull(coordinates, "Required parameter coordinates must be specified.");
      this.categoryId = com.google.api.client.util.Preconditions.checkNotNull(categoryId, "Required parameter categoryId must be specified.");
    }

    @Override
    public AddVenueGPSInfo setAlt(java.lang.String alt) {
      return (AddVenueGPSInfo) super.setAlt(alt);
    }

    @Override
    public AddVenueGPSInfo setFields(java.lang.String fields) {
      return (AddVenueGPSInfo) super.setFields(fields);
    }

    @Override
    public AddVenueGPSInfo setKey(java.lang.String key) {
      return (AddVenueGPSInfo) super.setKey(key);
    }

    @Override
    public AddVenueGPSInfo setOauthToken(java.lang.String oauthToken) {
      return (AddVenueGPSInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public AddVenueGPSInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddVenueGPSInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddVenueGPSInfo setQuotaUser(java.lang.String quotaUser) {
      return (AddVenueGPSInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddVenueGPSInfo setUserIp(java.lang.String userIp) {
      return (AddVenueGPSInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long userId;

    /**

     */
    public java.lang.Long getUserId() {
      return userId;
    }

    public AddVenueGPSInfo setUserId(java.lang.Long userId) {
      this.userId = userId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public AddVenueGPSInfo setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String coordinates;

    /**

     */
    public java.lang.String getCoordinates() {
      return coordinates;
    }

    public AddVenueGPSInfo setCoordinates(java.lang.String coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String categoryId;

    /**

     */
    public java.lang.String getCategoryId() {
      return categoryId;
    }

    public AddVenueGPSInfo setCategoryId(java.lang.String categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String phone;

    /**

     */
    public java.lang.String getPhone() {
      return phone;
    }

    public AddVenueGPSInfo setPhone(java.lang.String phone) {
      this.phone = phone;
      return this;
    }

    @Override
    public AddVenueGPSInfo set(String parameterName, Object value) {
      return (AddVenueGPSInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "findDistanceAndWalkingDuration".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link FindDistanceAndWalkingDuration#execute()} method to invoke
   * the remote operation.
   *
   * @param origLat
   * @param origLong
   * @param venuesCoord
   * @return the request
   */
  public FindDistanceAndWalkingDuration findDistanceAndWalkingDuration(java.lang.Double origLat, java.lang.Double origLong, java.lang.String venuesCoord) throws java.io.IOException {
    FindDistanceAndWalkingDuration result = new FindDistanceAndWalkingDuration(origLat, origLong, venuesCoord);
    initialize(result);
    return result;
  }

  public class FindDistanceAndWalkingDuration extends FoursquareendpointRequest<it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject> {

    private static final String REST_PATH = "findDistanceAndWalkingDuration/{origLat}/{origLong}/{venuesCoord}";

    /**
     * Create a request for the method "findDistanceAndWalkingDuration".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link FindDistanceAndWalkingDuration#execute()} method to
     * invoke the remote operation. <p> {@link FindDistanceAndWalkingDuration#initialize(com.google.ap
     * i.client.googleapis.services.AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @param origLat
     * @param origLong
     * @param venuesCoord
     * @since 1.13
     */
    protected FindDistanceAndWalkingDuration(java.lang.Double origLat, java.lang.Double origLong, java.lang.String venuesCoord) {
      super(Foursquareendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject.class);
      this.origLat = com.google.api.client.util.Preconditions.checkNotNull(origLat, "Required parameter origLat must be specified.");
      this.origLong = com.google.api.client.util.Preconditions.checkNotNull(origLong, "Required parameter origLong must be specified.");
      this.venuesCoord = com.google.api.client.util.Preconditions.checkNotNull(venuesCoord, "Required parameter venuesCoord must be specified.");
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
    public FindDistanceAndWalkingDuration setAlt(java.lang.String alt) {
      return (FindDistanceAndWalkingDuration) super.setAlt(alt);
    }

    @Override
    public FindDistanceAndWalkingDuration setFields(java.lang.String fields) {
      return (FindDistanceAndWalkingDuration) super.setFields(fields);
    }

    @Override
    public FindDistanceAndWalkingDuration setKey(java.lang.String key) {
      return (FindDistanceAndWalkingDuration) super.setKey(key);
    }

    @Override
    public FindDistanceAndWalkingDuration setOauthToken(java.lang.String oauthToken) {
      return (FindDistanceAndWalkingDuration) super.setOauthToken(oauthToken);
    }

    @Override
    public FindDistanceAndWalkingDuration setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindDistanceAndWalkingDuration) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindDistanceAndWalkingDuration setQuotaUser(java.lang.String quotaUser) {
      return (FindDistanceAndWalkingDuration) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindDistanceAndWalkingDuration setUserIp(java.lang.String userIp) {
      return (FindDistanceAndWalkingDuration) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Double origLat;

    /**

     */
    public java.lang.Double getOrigLat() {
      return origLat;
    }

    public FindDistanceAndWalkingDuration setOrigLat(java.lang.Double origLat) {
      this.origLat = origLat;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double origLong;

    /**

     */
    public java.lang.Double getOrigLong() {
      return origLong;
    }

    public FindDistanceAndWalkingDuration setOrigLong(java.lang.Double origLong) {
      this.origLong = origLong;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String venuesCoord;

    /**

     */
    public java.lang.String getVenuesCoord() {
      return venuesCoord;
    }

    public FindDistanceAndWalkingDuration setVenuesCoord(java.lang.String venuesCoord) {
      this.venuesCoord = venuesCoord;
      return this;
    }

    @Override
    public FindDistanceAndWalkingDuration set(String parameterName, Object value) {
      return (FindDistanceAndWalkingDuration) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "findInfoAddress".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link FindInfoAddress#execute()} method to invoke the remote
   * operation.
   *
   * @param coordinates
   * @return the request
   */
  public FindInfoAddress findInfoAddress(java.lang.String coordinates) throws java.io.IOException {
    FindInfoAddress result = new FindInfoAddress(coordinates);
    initialize(result);
    return result;
  }

  public class FindInfoAddress extends FoursquareendpointRequest<it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.StringCollection> {

    private static final String REST_PATH = "findInfoAddress/{coordinates}";

    /**
     * Create a request for the method "findInfoAddress".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link FindInfoAddress#execute()} method to invoke the remote
     * operation. <p> {@link FindInfoAddress#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param coordinates
     * @since 1.13
     */
    protected FindInfoAddress(java.lang.String coordinates) {
      super(Foursquareendpoint.this, "GET", REST_PATH, null, it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.StringCollection.class);
      this.coordinates = com.google.api.client.util.Preconditions.checkNotNull(coordinates, "Required parameter coordinates must be specified.");
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
    public FindInfoAddress setAlt(java.lang.String alt) {
      return (FindInfoAddress) super.setAlt(alt);
    }

    @Override
    public FindInfoAddress setFields(java.lang.String fields) {
      return (FindInfoAddress) super.setFields(fields);
    }

    @Override
    public FindInfoAddress setKey(java.lang.String key) {
      return (FindInfoAddress) super.setKey(key);
    }

    @Override
    public FindInfoAddress setOauthToken(java.lang.String oauthToken) {
      return (FindInfoAddress) super.setOauthToken(oauthToken);
    }

    @Override
    public FindInfoAddress setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindInfoAddress) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindInfoAddress setQuotaUser(java.lang.String quotaUser) {
      return (FindInfoAddress) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindInfoAddress setUserIp(java.lang.String userIp) {
      return (FindInfoAddress) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String coordinates;

    /**

     */
    public java.lang.String getCoordinates() {
      return coordinates;
    }

    public FindInfoAddress setCoordinates(java.lang.String coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    @Override
    public FindInfoAddress set(String parameterName, Object value) {
      return (FindInfoAddress) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "findVenuesCategories".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link FindVenuesCategories#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public FindVenuesCategories findVenuesCategories() throws java.io.IOException {
    FindVenuesCategories result = new FindVenuesCategories();
    initialize(result);
    return result;
  }

  public class FindVenuesCategories extends FoursquareendpointRequest<it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.JsonMap> {

    private static final String REST_PATH = "findVenuesCategories";

    /**
     * Create a request for the method "findVenuesCategories".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link FindVenuesCategories#execute()} method to invoke the
     * remote operation. <p> {@link FindVenuesCategories#initialize(com.google.api.client.googleapis.s
     * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected FindVenuesCategories() {
      super(Foursquareendpoint.this, "POST", REST_PATH, null, it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.JsonMap.class);
    }

    @Override
    public FindVenuesCategories setAlt(java.lang.String alt) {
      return (FindVenuesCategories) super.setAlt(alt);
    }

    @Override
    public FindVenuesCategories setFields(java.lang.String fields) {
      return (FindVenuesCategories) super.setFields(fields);
    }

    @Override
    public FindVenuesCategories setKey(java.lang.String key) {
      return (FindVenuesCategories) super.setKey(key);
    }

    @Override
    public FindVenuesCategories setOauthToken(java.lang.String oauthToken) {
      return (FindVenuesCategories) super.setOauthToken(oauthToken);
    }

    @Override
    public FindVenuesCategories setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindVenuesCategories) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindVenuesCategories setQuotaUser(java.lang.String quotaUser) {
      return (FindVenuesCategories) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindVenuesCategories setUserIp(java.lang.String userIp) {
      return (FindVenuesCategories) super.setUserIp(userIp);
    }

    @Override
    public FindVenuesCategories set(String parameterName, Object value) {
      return (FindVenuesCategories) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "performTokenRequest".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link PerformTokenRequest#execute()} method to invoke the remote
   * operation.
   *
   * @param code
   * @param userId
   * @return the request
   */
  public PerformTokenRequest performTokenRequest(java.lang.String code, java.lang.Long userId) throws java.io.IOException {
    PerformTokenRequest result = new PerformTokenRequest(code, userId);
    initialize(result);
    return result;
  }

  public class PerformTokenRequest extends FoursquareendpointRequest<Void> {

    private static final String REST_PATH = "performTokenRequest/{code}/{userId}";

    /**
     * Create a request for the method "performTokenRequest".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link PerformTokenRequest#execute()} method to invoke the
     * remote operation. <p> {@link PerformTokenRequest#initialize(com.google.api.client.googleapis.se
     * rvices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param code
     * @param userId
     * @since 1.13
     */
    protected PerformTokenRequest(java.lang.String code, java.lang.Long userId) {
      super(Foursquareendpoint.this, "GET", REST_PATH, null, Void.class);
      this.code = com.google.api.client.util.Preconditions.checkNotNull(code, "Required parameter code must be specified.");
      this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
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
    public PerformTokenRequest setAlt(java.lang.String alt) {
      return (PerformTokenRequest) super.setAlt(alt);
    }

    @Override
    public PerformTokenRequest setFields(java.lang.String fields) {
      return (PerformTokenRequest) super.setFields(fields);
    }

    @Override
    public PerformTokenRequest setKey(java.lang.String key) {
      return (PerformTokenRequest) super.setKey(key);
    }

    @Override
    public PerformTokenRequest setOauthToken(java.lang.String oauthToken) {
      return (PerformTokenRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public PerformTokenRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (PerformTokenRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public PerformTokenRequest setQuotaUser(java.lang.String quotaUser) {
      return (PerformTokenRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public PerformTokenRequest setUserIp(java.lang.String userIp) {
      return (PerformTokenRequest) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String code;

    /**

     */
    public java.lang.String getCode() {
      return code;
    }

    public PerformTokenRequest setCode(java.lang.String code) {
      this.code = code;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long userId;

    /**

     */
    public java.lang.Long getUserId() {
      return userId;
    }

    public PerformTokenRequest setUserId(java.lang.Long userId) {
      this.userId = userId;
      return this;
    }

    @Override
    public PerformTokenRequest set(String parameterName, Object value) {
      return (PerformTokenRequest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "searchVenues".
   *
   * This request holds the parameters needed by the foursquareendpoint server.  After setting any
   * optional parameters, call the {@link SearchVenues#execute()} method to invoke the remote
   * operation.
   *
   * @param ll
   * @return the request
   */
  public SearchVenues searchVenues(java.lang.String ll) throws java.io.IOException {
    SearchVenues result = new SearchVenues(ll);
    initialize(result);
    return result;
  }

  public class SearchVenues extends FoursquareendpointRequest<it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject> {

    private static final String REST_PATH = "searchVenues/{ll}";

    /**
     * Create a request for the method "searchVenues".
     *
     * This request holds the parameters needed by the the foursquareendpoint server.  After setting
     * any optional parameters, call the {@link SearchVenues#execute()} method to invoke the remote
     * operation. <p> {@link
     * SearchVenues#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param ll
     * @since 1.13
     */
    protected SearchVenues(java.lang.String ll) {
      super(Foursquareendpoint.this, "POST", REST_PATH, null, it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject.class);
      this.ll = com.google.api.client.util.Preconditions.checkNotNull(ll, "Required parameter ll must be specified.");
    }

    @Override
    public SearchVenues setAlt(java.lang.String alt) {
      return (SearchVenues) super.setAlt(alt);
    }

    @Override
    public SearchVenues setFields(java.lang.String fields) {
      return (SearchVenues) super.setFields(fields);
    }

    @Override
    public SearchVenues setKey(java.lang.String key) {
      return (SearchVenues) super.setKey(key);
    }

    @Override
    public SearchVenues setOauthToken(java.lang.String oauthToken) {
      return (SearchVenues) super.setOauthToken(oauthToken);
    }

    @Override
    public SearchVenues setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SearchVenues) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SearchVenues setQuotaUser(java.lang.String quotaUser) {
      return (SearchVenues) super.setQuotaUser(quotaUser);
    }

    @Override
    public SearchVenues setUserIp(java.lang.String userIp) {
      return (SearchVenues) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String ll;

    /**

     */
    public java.lang.String getLl() {
      return ll;
    }

    public SearchVenues setLl(java.lang.String ll) {
      this.ll = ll;
      return this;
    }

    @Override
    public SearchVenues set(String parameterName, Object value) {
      return (SearchVenues) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Foursquareendpoint}.
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

    /** Builds a new instance of {@link Foursquareendpoint}. */
    @Override
    public Foursquareendpoint build() {
      return new Foursquareendpoint(this);
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
     * Set the {@link FoursquareendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setFoursquareendpointRequestInitializer(
        FoursquareendpointRequestInitializer foursquareendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(foursquareendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
