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
 * on 2014-09-20 at 15:27:43 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.initiativeendpoint.model;

/**
 * Model definition for Initiative.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the initiativeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Initiative extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime beginningDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String category;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String location;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String picture;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String text;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime timestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;
  
  private Integer numOfComments;
    

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getBeginningDate() {
    return beginningDate;
  }

  /**
   * @param beginningDate beginningDate or {@code null} for none
   */
  public Initiative setBeginningDate(com.google.api.client.util.DateTime beginningDate) {
    this.beginningDate = beginningDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCategory() {
    return category;
  }

  /**
   * @param category category or {@code null} for none
   */
  public Initiative setCategory(java.lang.String category) {
    this.category = category;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Initiative setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLocation() {
    return location;
  }

  /**
   * @param location location or {@code null} for none
   */
  public Initiative setLocation(java.lang.String location) {
    this.location = location;
    return this;
  }

  /**
   * @see #decodePicture()
   * @return value or {@code null} for none
   */
  public java.lang.String getPicture() {
    return picture;
  }

  /**

   * @see #getPicture()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodePicture() {
    return com.google.api.client.util.Base64.decodeBase64(picture);
  }

  /**
   * @see #encodePicture()
   * @param picture picture or {@code null} for none
   */
  public Initiative setPicture(java.lang.String picture) {
    this.picture = picture;
    return this;
  }

  /**

   * @see #setPicture()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public Initiative encodePicture(byte[] picture) {
    this.picture = com.google.api.client.util.Base64.encodeBase64URLSafeString(picture);
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getText() {
    return text;
  }

  /**
   * @param text text or {@code null} for none
   */
  public Initiative setText(java.lang.String text) {
    this.text = text;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp timestamp or {@code null} for none
   */
  public Initiative setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public Initiative setTitle(java.lang.String title) {
    this.title = title;
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
  public Initiative setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public Initiative set(String fieldName, Object value) {
    return (Initiative) super.set(fieldName, value);
  }

  @Override
  public Initiative clone() {
    return (Initiative) super.clone();
  }

public Integer getNumOfComments() {
	return numOfComments;
}

public void setNumOfComments(Integer numOfComments) {
	this.numOfComments = numOfComments;
}

}
