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
 * on 2014-10-26 at 20:58:44 UTC 
 * Modify at your own risk.
 */

package it.polimi.dima.polisocial.entity.poliuserendpoint.model;

import android.graphics.Bitmap;

/**
 * Model definition for UserDTO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the poliuserendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserDTO extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer age;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String faculty;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nickname;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;
  
  private Bitmap bitmap;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAge() {
    return age;
  }

  /**
   * @param age age or {@code null} for none
   */
  public UserDTO setAge(java.lang.Integer age) {
    this.age = age;
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
  public UserDTO setFaculty(java.lang.String faculty) {
    this.faculty = faculty;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNickname() {
    return nickname;
  }

  /**
   * @param nickname nickname or {@code null} for none
   */
  public UserDTO setNickname(java.lang.String nickname) {
    this.nickname = nickname;
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
  public UserDTO setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public UserDTO set(String fieldName, Object value) {
    return (UserDTO) super.set(fieldName, value);
  }

  @Override
  public UserDTO clone() {
    return (UserDTO) super.clone();
  }

public Bitmap getBitmap() {
	return bitmap;
}

public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
}

}
