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
 * on 2014-10-28 at 10:56:59 UTC 
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
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long authorId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String authorName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long commentId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime commentTimestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long numOfComponents;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long postId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String text;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String type;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getAuthorId() {
    return authorId;
  }

  /**
   * @param authorId authorId or {@code null} for none
   */
  public Comment setAuthorId(java.lang.Long authorId) {
    this.authorId = authorId;
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
  public java.lang.Long getCommentId() {
    return commentId;
  }

  /**
   * @param commentId commentId or {@code null} for none
   */
  public Comment setCommentId(java.lang.Long commentId) {
    this.commentId = commentId;
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
  public java.lang.Long getNumOfComponents() {
    return numOfComponents;
  }

  /**
   * @param numOfComponents numOfComponents or {@code null} for none
   */
  public Comment setNumOfComponents(java.lang.Long numOfComponents) {
    this.numOfComponents = numOfComponents;
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
  public Comment setPostId(java.lang.Long postId) {
    this.postId = postId;
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

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getType() {
    return type;
  }

  /**
   * @param type type or {@code null} for none
   */
  public Comment setType(java.lang.String type) {
    this.type = type;
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
