/**
 * 
 */
package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * @author danturi
 * Notification entity used to inform users of new events happened
 */
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	// Id user that has the notification
	private Integer userId;
	
	//Id of the post
	private Integer postId;
	
	//type of Post : Spotted,Notice board, Initiative
	private String typePost;

	// Notification read or not
	private Boolean readFlag;
	
	private Date timestamp;

	
	
	public Key getKey() {
		return key;
	}

	public void clearKey() {
		this.key = null;
	}

	public String getTypePost() {
		return typePost;
	}

	public void setTypePost(String typePost) {
		this.typePost = typePost;
	}

	public Boolean getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
