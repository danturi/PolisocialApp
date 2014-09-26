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
 * on 2014-09-26 at 03:22:24 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.poliuserendpoint.model;

/**
 * Model definition for PoliUser.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the poliuserendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PoliUser extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer age;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String faculty;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Text favoriteBooksMoviesShowsMusic;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fbaccount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer height;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imReallyGoodAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nickname;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifiedAnnouncement;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifiedEvent;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifiedHitOn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifiedSpotted;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifyAnnouncement;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifyEvent;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean notifySpotted;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private PhoneNumber phoneNumber;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String profilePicture1;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String profilePicture2;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String selfSummary;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sixThingsWithout;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tokenFsq;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private User userGoogleAccount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String whatImDoingWithMyLife;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAge() {
    return age;
  }

  /**
   * @param age age or {@code null} for none
   */
  public PoliUser setAge(java.lang.Integer age) {
    this.age = age;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public PoliUser setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFaculty() {
    return faculty;
  }

  /**
   * @param faculty faculty or {@code null} for none
   */
  public PoliUser setFaculty(java.lang.String faculty) {
    this.faculty = faculty;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Text getFavoriteBooksMoviesShowsMusic() {
    return favoriteBooksMoviesShowsMusic;
  }

  /**
   * @param favoriteBooksMoviesShowsMusic favoriteBooksMoviesShowsMusic or {@code null} for none
   */
  public PoliUser setFavoriteBooksMoviesShowsMusic(Text favoriteBooksMoviesShowsMusic) {
    this.favoriteBooksMoviesShowsMusic = favoriteBooksMoviesShowsMusic;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFbaccount() {
    return fbaccount;
  }

  /**
   * @param fbaccount fbaccount or {@code null} for none
   */
  public PoliUser setFbaccount(java.lang.String fbaccount) {
    this.fbaccount = fbaccount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getHeight() {
    return height;
  }

  /**
   * @param height height or {@code null} for none
   */
  public PoliUser setHeight(java.lang.Integer height) {
    this.height = height;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImReallyGoodAt() {
    return imReallyGoodAt;
  }

  /**
   * @param imReallyGoodAt imReallyGoodAt or {@code null} for none
   */
  public PoliUser setImReallyGoodAt(java.lang.String imReallyGoodAt) {
    this.imReallyGoodAt = imReallyGoodAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNickname() {
    return nickname;
  }

  /**
   * @param nickname nickname or {@code null} for none
   */
  public PoliUser setNickname(java.lang.String nickname) {
    this.nickname = nickname;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifiedAnnouncement() {
    return notifiedAnnouncement;
  }

  /**
   * @param notifiedAnnouncement notifiedAnnouncement or {@code null} for none
   */
  public PoliUser setNotifiedAnnouncement(java.lang.Boolean notifiedAnnouncement) {
    this.notifiedAnnouncement = notifiedAnnouncement;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifiedEvent() {
    return notifiedEvent;
  }

  /**
   * @param notifiedEvent notifiedEvent or {@code null} for none
   */
  public PoliUser setNotifiedEvent(java.lang.Boolean notifiedEvent) {
    this.notifiedEvent = notifiedEvent;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifiedHitOn() {
    return notifiedHitOn;
  }

  /**
   * @param notifiedHitOn notifiedHitOn or {@code null} for none
   */
  public PoliUser setNotifiedHitOn(java.lang.Boolean notifiedHitOn) {
    this.notifiedHitOn = notifiedHitOn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifiedSpotted() {
    return notifiedSpotted;
  }

  /**
   * @param notifiedSpotted notifiedSpotted or {@code null} for none
   */
  public PoliUser setNotifiedSpotted(java.lang.Boolean notifiedSpotted) {
    this.notifiedSpotted = notifiedSpotted;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifyAnnouncement() {
    return notifyAnnouncement;
  }

  /**
   * @param notifyAnnouncement notifyAnnouncement or {@code null} for none
   */
  public PoliUser setNotifyAnnouncement(java.lang.Boolean notifyAnnouncement) {
    this.notifyAnnouncement = notifyAnnouncement;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifyEvent() {
    return notifyEvent;
  }

  /**
   * @param notifyEvent notifyEvent or {@code null} for none
   */
  public PoliUser setNotifyEvent(java.lang.Boolean notifyEvent) {
    this.notifyEvent = notifyEvent;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getNotifySpotted() {
    return notifySpotted;
  }

  /**
   * @param notifySpotted notifySpotted or {@code null} for none
   */
  public PoliUser setNotifySpotted(java.lang.Boolean notifySpotted) {
    this.notifySpotted = notifySpotted;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public PoliUser setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber phoneNumber or {@code null} for none
   */
  public PoliUser setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * @see #decodeProfilePicture1()
   * @return value or {@code null} for none
   */
  public java.lang.String getProfilePicture1() {
    return profilePicture1;
  }

  /**

   * @see #getProfilePicture1()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeProfilePicture1() {
    return com.google.api.client.util.Base64.decodeBase64(profilePicture1);
  }

  /**
   * @see #encodeProfilePicture1()
   * @param profilePicture1 profilePicture1 or {@code null} for none
   */
  public PoliUser setProfilePicture1(java.lang.String profilePicture1) {
    this.profilePicture1 = profilePicture1;
    return this;
  }

  /**

   * @see #setProfilePicture1()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public PoliUser encodeProfilePicture1(byte[] profilePicture1) {
    this.profilePicture1 = com.google.api.client.util.Base64.encodeBase64URLSafeString(profilePicture1);
    return this;
  }

  /**
   * @see #decodeProfilePicture2()
   * @return value or {@code null} for none
   */
  public java.lang.String getProfilePicture2() {
    return profilePicture2;
  }

  /**

   * @see #getProfilePicture2()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeProfilePicture2() {
    return com.google.api.client.util.Base64.decodeBase64(profilePicture2);
  }

  /**
   * @see #encodeProfilePicture2()
   * @param profilePicture2 profilePicture2 or {@code null} for none
   */
  public PoliUser setProfilePicture2(java.lang.String profilePicture2) {
    this.profilePicture2 = profilePicture2;
    return this;
  }

  /**

   * @see #setProfilePicture2()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public PoliUser encodeProfilePicture2(byte[] profilePicture2) {
    this.profilePicture2 = com.google.api.client.util.Base64.encodeBase64URLSafeString(profilePicture2);
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSelfSummary() {
    return selfSummary;
  }

  /**
   * @param selfSummary selfSummary or {@code null} for none
   */
  public PoliUser setSelfSummary(java.lang.String selfSummary) {
    this.selfSummary = selfSummary;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSixThingsWithout() {
    return sixThingsWithout;
  }

  /**
   * @param sixThingsWithout sixThingsWithout or {@code null} for none
   */
  public PoliUser setSixThingsWithout(java.lang.String sixThingsWithout) {
    this.sixThingsWithout = sixThingsWithout;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTokenFsq() {
    return tokenFsq;
  }

  /**
   * @param tokenFsq tokenFsq or {@code null} for none
   */
  public PoliUser setTokenFsq(java.lang.String tokenFsq) {
    this.tokenFsq = tokenFsq;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public User getUserGoogleAccount() {
    return userGoogleAccount;
  }

  /**
   * @param userGoogleAccount userGoogleAccount or {@code null} for none
   */
  public PoliUser setUserGoogleAccount(User userGoogleAccount) {
    this.userGoogleAccount = userGoogleAccount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public PoliUser setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getWhatImDoingWithMyLife() {
    return whatImDoingWithMyLife;
  }

  /**
   * @param whatImDoingWithMyLife whatImDoingWithMyLife or {@code null} for none
   */
  public PoliUser setWhatImDoingWithMyLife(java.lang.String whatImDoingWithMyLife) {
    this.whatImDoingWithMyLife = whatImDoingWithMyLife;
    return this;
  }

  @Override
  public PoliUser set(String fieldName, Object value) {
    return (PoliUser) super.set(fieldName, value);
  }

  @Override
  public PoliUser clone() {
    return (PoliUser) super.clone();
  }

}
