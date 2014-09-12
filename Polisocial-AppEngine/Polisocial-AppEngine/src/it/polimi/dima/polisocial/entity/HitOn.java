package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
/**
 * @author buzz
 * HitOn entity that a user can attach to a spotted post belonging to "love" category
 */

@Entity
public class HitOn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Key seducerKey;
	private Key postKey;
	private String contact;
	private String authorName;
	private Date timestamp;
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getSeducerKey() {
		return seducerKey;
	}
	public void setSeducerKey(Key seducerKey) {
		this.seducerKey = seducerKey;
	}
	public Key getPostKey() {
		return postKey;
	}
	public void setPostKey(Key postKey) {
		this.postKey = postKey;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
