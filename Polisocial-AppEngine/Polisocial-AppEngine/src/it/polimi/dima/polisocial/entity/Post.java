/**
 * 
 */
package it.polimi.dima.polisocial.entity;

import java.util.Date;

import com.google.appengine.api.datastore.Blob;

/**
 * @author danturi
 *
 */
public abstract class Post {


		
		// Id user that write the post
		private Integer userId;
		
		private String text;
		
		private Blob photo;
		
		private Date timestamp;
		
		
		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Blob getPhoto() {
			return photo;
		}

		public void setPhoto(Blob photo) {
			this.photo = photo;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}

		
}
