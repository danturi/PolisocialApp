package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Initiative extends Post {

	private String category;

	private Date beginningDate;

	private String location;
	
	private Integer numOfLikes;
	
	private Integer numOfGoing;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getNumOfLikes() {
		return numOfLikes;
	}

	public void setNumOfLikes(Integer numOfLikes) {
		this.numOfLikes = numOfLikes;
	}

	public Integer getNumOfGoing() {
		return numOfGoing;
	}

	public void setNumOfGoing(Integer numOfGoing) {
		this.numOfGoing = numOfGoing;
	}

}
