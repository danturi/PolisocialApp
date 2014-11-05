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
 * on 2014-11-04 at 23:33:56 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.secondhandbookendpoint.model;

/**
 * Model definition for SecondHandBook.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the secondhandbookendpoint. For a detailed explanation
 * see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SecondHandBook extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> authorsBook;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String faculty;

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
  private java.lang.String isbn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double price;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer publishedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String publisher;

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
  public java.util.List<java.lang.String> getAuthorsBook() {
    return authorsBook;
  }

  /**
   * @param authorsBook authorsBook or {@code null} for none
   */
  public SecondHandBook setAuthorsBook(java.util.List<java.lang.String> authorsBook) {
    this.authorsBook = authorsBook;
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
  public SecondHandBook setFaculty(java.lang.String faculty) {
    this.faculty = faculty;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHavePicture() {
    return havePicture;
  }

  /**
   * @param havePicture havePicture or {@code null} for none
   */
  public SecondHandBook setHavePicture(java.lang.Boolean havePicture) {
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
  public SecondHandBook setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIsbn() {
    return isbn;
  }

  /**
   * @param isbn isbn or {@code null} for none
   */
  public SecondHandBook setIsbn(java.lang.String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getPrice() {
    return price;
  }

  /**
   * @param price price or {@code null} for none
   */
  public SecondHandBook setPrice(java.lang.Double price) {
    this.price = price;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPublishedDate() {
    return publishedDate;
  }

  /**
   * @param publishedDate publishedDate or {@code null} for none
   */
  public SecondHandBook setPublishedDate(java.lang.Integer publishedDate) {
    this.publishedDate = publishedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPublisher() {
    return publisher;
  }

  /**
   * @param publisher publisher or {@code null} for none
   */
  public SecondHandBook setPublisher(java.lang.String publisher) {
    this.publisher = publisher;
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
  public SecondHandBook setText(java.lang.String text) {
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
  public SecondHandBook setTimestamp(com.google.api.client.util.DateTime timestamp) {
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
  public SecondHandBook setTitle(java.lang.String title) {
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
  public SecondHandBook setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public SecondHandBook set(String fieldName, Object value) {
    return (SecondHandBook) super.set(fieldName, value);
  }

  @Override
  public SecondHandBook clone() {
    return (SecondHandBook) super.clone();
  }

}
