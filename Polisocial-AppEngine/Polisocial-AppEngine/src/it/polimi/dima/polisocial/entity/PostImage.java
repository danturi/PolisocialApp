package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;

@Entity
public class PostImage {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Post postId;
	private Blob  image;
	
	public Post getPostId() {
		return postId;
	}
	public void setPostId(Post postId) {
		this.postId = postId;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public Long getId() {
		return id;
	}
}
