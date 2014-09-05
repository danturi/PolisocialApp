package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

import com.google.appengine.api.datastore.Key;


/**
 * @author buzz
 * Comment entity that a user can write below a post
 */

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key commentKey;
	private Key authorKey;
	private Key postKey;
	private String text;
	private String authorName;
	private Date commentTimestamp;
	private String type;  // serve sapere a che tipo di post corrisponde il commento
	
	public Key getCommentKey() {
		return commentKey;
	}
	public void setCommentKey(Key commentKey) {
		this.commentKey = commentKey;
	}
	public Key getAuthorKey() {
		return authorKey;
	}
	public void setAuthorKey(Key authorKey) {
		this.authorKey = authorKey;
	}
	public Key getPostKey() {
		return postKey;
	}
	public void setPostKey(Key postKey) {
		this.postKey = postKey;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public Date getCommentTimestamp() {
		return commentTimestamp;
	}
	public void setCommentTimestamp(Date commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
