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
		private Long id;
	
		// Id user that write the post
		private Long userId;
		
		private String text;
		
		//private Blob picture;
		
		private String title;
		
		private Date timestamp;
		
		private Boolean havePicture;
		
		
		public Long getId(){
			return id;
		}
		
		public void setId(Long id){
			this.id=id;
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


		public Date getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}

		/*
		public Blob getPicture() {
			return picture;
		}

		public void setPicture(Blob picture) {
			this.picture = picture;
		}
		*/

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}


		public Boolean getHavePicture() {
			return havePicture;
		}


		public void setHavePicture(Boolean havePicture) {
			this.havePicture = havePicture;
		}


		


		
}
