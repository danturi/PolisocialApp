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
 * (build: 2014-10-28 17:08:27 UTC)
 * on 2014-11-16 at 16:38:16 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.hitonendpoint.model;

/**
 * Model definition for HitOn.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the hitonendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class HitOn extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String authorName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String contact;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long postId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long seducerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime timestamp;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAuthorName() {
    return authorName;
  }

  /**
   * @param authorName authorName or {@code null} for none
   */
  public HitOn setAuthorName(java.lang.String authorName) {
    this.authorName = authorName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getContact() {
    return contact;
  }

  /**
   * @param contact contact or {@code null} for none
   */
  public HitOn setContact(java.lang.String contact) {
    this.contact = contact;
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
  public HitOn setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getPostId() {
    return postId;
  }

  /**
   * @param postId postId or {@code null} for none
   */
  public HitOn setPostId(java.lang.Long postId) {
    this.postId = postId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getSeducerId() {
    return seducerId;
  }

  /**
   * @param seducerId seducerId or {@code null} for none
   */
  public HitOn setSeducerId(java.lang.Long seducerId) {
    this.seducerId = seducerId;
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
  public HitOn setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public HitOn set(String fieldName, Object value) {
    return (HitOn) super.set(fieldName, value);
  }

  @Override
  public HitOn clone() {
    return (HitOn) super.clone();
  }

}
