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
 * (build: 2014-07-09 17:08:39 UTC)
 * on 2014-07-15 at 01:35:48 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.privatelessonendpoint.model;

/**
 * Model definition for PrivateLesson.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the privatelessonendpoint. For a detailed explanation
 * see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PrivateLesson extends com.google.api.client.json.GenericJson {

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
  public PrivateLesson setKey(Key key) {
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
  public PrivateLesson setPhoto(java.lang.String photo) {
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
  public PrivateLesson encodePhoto(byte[] photo) {
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
  public PrivateLesson setText(java.lang.String text) {
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
  public PrivateLesson setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
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
  public PrivateLesson setUserId(java.lang.Integer userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public PrivateLesson set(String fieldName, Object value) {
    return (PrivateLesson) super.set(fieldName, value);
  }

  @Override
  public PrivateLesson clone() {
    return (PrivateLesson) super.clone();
  }

}
