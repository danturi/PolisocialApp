package it.polimi.dima.polisocial;

public class NotificationItem {
	private long id, postId;
	private String postTitle, timeStamp, category;

	public NotificationItem() {
	}

	public NotificationItem(int id, long postId, String postTitle, String timeStamp, String category) {
		super();
		this.id = id;
		this.postId = postId;
		this.postTitle = postTitle;
		this.timeStamp = timeStamp;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
