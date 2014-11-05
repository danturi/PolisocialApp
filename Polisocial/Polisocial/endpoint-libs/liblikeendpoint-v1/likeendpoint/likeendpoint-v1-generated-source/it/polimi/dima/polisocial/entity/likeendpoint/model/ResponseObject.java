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
 * on 2014-11-04 at 23:37:03 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.likeendpoint.model;

/**
 * Model definition for ResponseObject.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the likeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ResponseObject extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String exception;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("object")
  private java.lang.Object object__;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getException() {
    return exception;
  }

  /**
   * @param exception exception or {@code null} for none
   */
  public ResponseObject setException(java.lang.String exception) {
    this.exception = exception;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Object getObject() {
    return object__;
  }

  /**
   * @param object__ object__ or {@code null} for none
   */
  public ResponseObject setObject(java.lang.Object object__) {
    this.object__ = object__;
    return this;
  }

  @Override
  public ResponseObject set(String fieldName, Object value) {
    return (ResponseObject) super.set(fieldName, value);
  }

  @Override
  public ResponseObject clone() {
    return (ResponseObject) super.clone();
  }

}
