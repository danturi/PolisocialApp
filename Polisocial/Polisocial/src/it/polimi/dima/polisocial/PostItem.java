package it.polimi.dima.polisocial;

import java.sql.Blob;



public class PostItem {
	private long id;
	private String name, text, timeStamp;
	private Blob image, profilePic;
	private int numbOfComments;

	public PostItem() {
	}

	public PostItem(int id, String name, Blob image, String text,
			Blob profilePic, String timeStamp, int numbOfComments) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.text = text;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.setNumbOfComments(numbOfComments);
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

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
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

	public int getNumbOfComments() {
		return numbOfComments;
	}

	public void setNumbOfComments(int numbOfComments) {
		this.numbOfComments = numbOfComments;
	}

}

