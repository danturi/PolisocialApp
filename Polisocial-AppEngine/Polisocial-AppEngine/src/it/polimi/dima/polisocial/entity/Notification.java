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
	private Long id;
	
	// Id user that has the notification
	private Long userId;
	
	//Id of the post
	private Long postId;
	
	//type of Post : Spotted,Rental...
	private String postType;

	// Notification read or not
	private Boolean readFlag;
	
	private Date timestamp;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypePost() {
		return postType;
	}

	public void setTypePost(String typePost) {
		this.postType = typePost;
	}

	public Boolean getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId2) {
		this.postId = postId2;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
