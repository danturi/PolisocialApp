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
 * on 2014-08-29 at 20:50:43 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.commentendpoint.model;

/**
 * Model definition for Comment.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the commentendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Comment extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key authorKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String authorName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key commentKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime commentTimestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key postKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String text;

  /**
   * @return value or {@code null} for none
   */
  public Key getAuthorKey() {
    return authorKey;
  }

  /**
   * @param authorKey authorKey or {@code null} for none
   */
  public Comment setAuthorKey(Key authorKey) {
    this.authorKey = authorKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAuthorName() {
    return authorName;
  }

  /**
   * @param authorName authorName or {@code null} for none
   */
  public Comment setAuthorName(java.lang.String authorName) {
    this.authorName = authorName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getCommentKey() {
    return commentKey;
  }

  /**
   * @param commentKey commentKey or {@code null} for none
   */
  public Comment setCommentKey(Key commentKey) {
    this.commentKey = commentKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCommentTimestamp() {
    return commentTimestamp;
  }

  /**
   * @param commentTimestamp commentTimestamp or {@code null} for none
   */
  public Comment setCommentTimestamp(com.google.api.client.util.DateTime commentTimestamp) {
    this.commentTimestamp = commentTimestamp;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getPostKey() {
    return postKey;
  }

  /**
   * @param postKey postKey or {@code null} for none
   */
  public Comment setPostKey(Key postKey) {
    this.postKey = postKey;
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
  public Comment setText(java.lang.String text) {
    this.text = text;
    return this;
  }

  @Override
  public Comment set(String fieldName, Object value) {
    return (Comment) super.set(fieldName, value);
  }

  @Override
  public Comment clone() {
    return (Comment) super.clone();
  }

}
