package it.polimi.dima.polisocial;

import java.util.Date;

public class EventItem {
	private long id;
	private String category;
	private Date beginningDate;
	private String title;
	private String shortDescription;
	private int numbOfComments;

	public EventItem() {
	}

	public EventItem(int id, String title, String shortDescription, Date beginningDate, int numbOfComments, String category) {
		super();
		this.id = id;
		this.title = title;
		this.category=category;
		this.shortDescription = shortDescription;
		this.beginningDate = beginningDate;
		this.numbOfComments = numbOfComments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getNumbOfComments() {
		return numbOfComments;
	}

	public void setNumbOfComments(int numbOfComments) {
		this.numbOfComments = numbOfComments;
	}

}
