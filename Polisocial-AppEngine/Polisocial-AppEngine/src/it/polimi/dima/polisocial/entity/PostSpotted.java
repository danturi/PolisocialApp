package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;


@Entity
public class PostSpotted extends Post {
	

	private String postCategory;
	private String postTitleAndLocation;


	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public String getPostTitleAndLocation() {
		return postTitleAndLocation;
	}

	public void setPostTitleAndLocation(String postTitleAndLocation) {
		this.postTitleAndLocation = postTitleAndLocation;
	}



}
