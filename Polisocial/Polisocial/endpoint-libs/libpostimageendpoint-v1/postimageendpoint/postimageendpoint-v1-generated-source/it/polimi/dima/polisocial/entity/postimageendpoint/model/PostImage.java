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
 * on 2014-10-28 at 10:58:13 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.postimageendpoint.model;

/**
 * Model definition for PostImage.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the postimageendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PostImage extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String image;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long postId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public PostImage setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @see #decodeImage()
   * @return value or {@code null} for none
   */
  public java.lang.String getImage() {
    return image;
  }

  /**

   * @see #getImage()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeImage() {
    return com.google.api.client.util.Base64.decodeBase64(image);
  }

  /**
   * @see #encodeImage()
   * @param image image or {@code null} for none
   */
  public PostImage setImage(java.lang.String image) {
    this.image = image;
    return this;
  }

  /**

   * @see #setImage()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public PostImage encodeImage(byte[] image) {
    this.image = com.google.api.client.util.Base64.encodeBase64URLSafeString(image);
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
  public PostImage setPostId(java.lang.Long postId) {
    this.postId = postId;
    return this;
  }

  @Override
  public PostImage set(String fieldName, Object value) {
    return (PostImage) super.set(fieldName, value);
  }

  @Override
  public PostImage clone() {
    return (PostImage) super.clone();
  }

}
