package it.polimi.dima.polisocial;

import java.sql.Blob;

public class CommentItem {
	private long id;
	private String name, text, timeStamp;
	private Blob profilePic;

	public CommentItem() {
	}

	public CommentItem(int id, String name, Blob image, String text,
			Blob profilePic, String timeStamp, int numbOfComments) {
		super();
		this.id = id;
		this.name = name;
		this.text = text;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTex() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Blob getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Blob profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}

