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
 * (build: 2014-06-09 16:41:44 UTC)
 * on 2014-07-03 at 21:08:18 UTC 
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
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String photo;

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
  @com.google.api.client.util.Key
  private java.lang.String typeOfInitiative;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer userId;

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Initiative setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @see #decodePhoto()
   * @return value or {@code null} for none
   */
  public java.lang.String getPhoto() {
    return photo;
  }

  /**

   * @see #getPhoto()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodePhoto() {
    return com.google.api.client.util.Base64.decodeBase64(photo);
  }

  /**
   * @see #encodePhoto()
   * @param photo photo or {@code null} for none
   */
  public Initiative setPhoto(java.lang.String photo) {
    this.photo = photo;
    return this;
  }

  /**

   * @see #setPhoto()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public Initiative encodePhoto(byte[] photo) {
    this.photo = com.google.api.client.util.Base64.encodeBase64URLSafeString(photo);
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
  public java.lang.String getTypeOfInitiative() {
    return typeOfInitiative;
  }

  /**
   * @param typeOfInitiative typeOfInitiative or {@code null} for none
   */
  public Initiative setTypeOfInitiative(java.lang.String typeOfInitiative) {
    this.typeOfInitiative = typeOfInitiative;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public Initiative setUserId(java.lang.Integer userId) {
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

}
