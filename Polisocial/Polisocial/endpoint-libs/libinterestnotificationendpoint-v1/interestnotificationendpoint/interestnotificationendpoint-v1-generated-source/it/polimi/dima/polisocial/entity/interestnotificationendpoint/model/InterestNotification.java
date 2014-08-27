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
 * on 2014-08-26 at 14:02:26 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.interestnotificationendpoint.model;

/**
 * Model definition for InterestNotification.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the interestnotificationendpoint. For a detailed
 * explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class InterestNotification extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer postId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean readFlag;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer seductorId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime timestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String typePost;

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
  public InterestNotification setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPostId() {
    return postId;
  }

  /**
   * @param postId postId or {@code null} for none
   */
  public InterestNotification setPostId(java.lang.Integer postId) {
    this.postId = postId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getReadFlag() {
    return readFlag;
  }

  /**
   * @param readFlag readFlag or {@code null} for none
   */
  public InterestNotification setReadFlag(java.lang.Boolean readFlag) {
    this.readFlag = readFlag;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSeductorId() {
    return seductorId;
  }

  /**
   * @param seductorId seductorId or {@code null} for none
   */
  public InterestNotification setSeductorId(java.lang.Integer seductorId) {
    this.seductorId = seductorId;
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
  public InterestNotification setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTypePost() {
    return typePost;
  }

  /**
   * @param typePost typePost or {@code null} for none
   */
  public InterestNotification setTypePost(java.lang.String typePost) {
    this.typePost = typePost;
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
  public InterestNotification setUserId(java.lang.Integer userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public InterestNotification set(String fieldName, Object value) {
    return (InterestNotification) super.set(fieldName, value);
  }

  @Override
  public InterestNotification clone() {
    return (InterestNotification) super.clone();
  }

}
