package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

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
	private Long commentId;
	private Long authorId;
	private Long postId;
	private String text;
	private String authorName;
	private Date commentTimestamp;
	private String type;  // serve sapere a che tipo di post corrisponde il commento
	@Transient
	private Long numOfComponents;
	
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	@Transient
	public Long getNumOfComponents() {
		return numOfComponents;
	}
	@Transient
	public void setNumOfComponents(Long numOfComponents) {
		this.numOfComponents = numOfComponents;
	}

	
	
	
}