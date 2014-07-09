package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;


// Notification used only for a user's interest in Spotted 
@Entity
public class InterestNotification extends Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	// User interested to a Spotted post
	private Integer seductorId;

	public Integer getSeductorId() {
		return seductorId;
	}

	public void setSeductorId(Integer seductorId) {
		this.seductorId = seductorId;
	}

}