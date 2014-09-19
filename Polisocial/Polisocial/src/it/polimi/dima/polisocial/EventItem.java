package it.polimi.dima.polisocial;

import java.util.Date;

public class EventItem {
	private long id;
	private String category;
	private Date beginningDate;
	private String title;
	private String description;
	private byte[] eventPicture;
	private int numbOfComments;
	private String timestamp;

	public EventItem() {
	}

	public EventItem(int id, byte[] eventPicture, String title, String description, Date beginningDate, int numbOfComments, String category, String timestamp) {
		super();
		this.id = id;
		this.eventPicture=eventPicture;
		this.title = title;
		this.category=category;
		this.description = description;
		this.beginningDate = beginningDate;
		this.setTimestamp(timestamp);
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
		return description;
	}

	public void setShortDescription(String shortDescription) {
		this.description = shortDescription;
	}

	public int getNumbOfComments() {
		return numbOfComments;
	}

	public void setNumbOfComments(int numbOfComments) {
		this.numbOfComments = numbOfComments;
	}

	public byte[] getEventPicture() {
		return eventPicture;
	}

	public void setEventPicture(byte[] eventPicture) {
		this.eventPicture = eventPicture;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
