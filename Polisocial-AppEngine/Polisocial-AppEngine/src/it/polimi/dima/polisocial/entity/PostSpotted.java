package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;


@Entity
public class PostSpotted extends Post {
	

	private String postCategory;


	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}


}
