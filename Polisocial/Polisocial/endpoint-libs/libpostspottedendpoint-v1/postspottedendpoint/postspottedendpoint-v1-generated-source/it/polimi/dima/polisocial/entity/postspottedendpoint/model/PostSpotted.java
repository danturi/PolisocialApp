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
 * on 2014-10-31 at 01:54:44 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.postspottedendpoint.model;

import android.graphics.Bitmap;

/**
 * Model definition for PostSpotted.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the postspottedendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PostSpotted extends com.google.api.client.json.GenericJson {

	
	private Bitmap bitmap;
	private Integer numOfComments;
  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean havePicture;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String postCategory;

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

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHavePicture() {
    return havePicture;
  }

  /**
   * @param havePicture havePicture or {@code null} for none
   */
  public PostSpotted setHavePicture(java.lang.Boolean havePicture) {
    this.havePicture = havePicture;
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
  public PostSpotted setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPostCategory() {
    return postCategory;
  }

  /**
   * @param postCategory postCategory or {@code null} for none
   */
  public PostSpotted setPostCategory(java.lang.String postCategory) {
    this.postCategory = postCategory;
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
  public PostSpotted setText(java.lang.String text) {
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
  public PostSpotted setTimestamp(com.google.api.client.util.DateTime timestamp) {
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
  public PostSpotted setTitle(java.lang.String title) {
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
  public PostSpotted setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public PostSpotted set(String fieldName, Object value) {
    return (PostSpotted) super.set(fieldName, value);
  }

  @Override
  public PostSpotted clone() {
    return (PostSpotted) super.clone();
  }

public Bitmap getBitmap() {
	return bitmap;
}

public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
}

public Integer getNumOfComments() {
	return numOfComments;
}

public void setNumOfComments(Integer numOfComments) {
	this.numOfComments = numOfComments;
}

}
