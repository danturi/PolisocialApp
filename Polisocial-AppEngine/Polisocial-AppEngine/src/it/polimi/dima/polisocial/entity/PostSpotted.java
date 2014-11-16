package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;


@Entity
public class PostSpotted extends Post {
	

	private String postCategory;
	private Integer numberLike;
	private Integer numberDislike;

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public Integer getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(Integer numberLike) {
		this.numberLike = numberLike;
	}

	public Integer getNumberDislike() {
		return numberDislike;
	}

	public void setNumberDislike(Integer numberDislike) {
		this.numberDislike = numberDislike;
	}


}
