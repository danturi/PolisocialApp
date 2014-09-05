/**
 * 
 */
package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

/**
 * @author danturi
 *
 */
@MappedSuperclass
public abstract class Post {


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Key key;
	
		// Id user that write the post
		private Long userId;
		
		private String text;
		
		private Blob photo;
		
		private Date timestamp;
		
		public Long getId(){
			return key.getId();
		}
		
		public Key getKey() {
			return key;
		}

		public void clearKey() {
			this.key = null;
		}
		
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
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
