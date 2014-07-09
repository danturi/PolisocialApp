package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class PostSpotted extends Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private Boolean flagAnonimity;
	
	public Key getKey() {
		return key;
	}

	public void clearKey() {
		this.key = null;
	}

	public Boolean isAnonimous() {
		return flagAnonimity;
	}

	public void setFlagAnonimity(Boolean flagAnonimity) {
		this.flagAnonimity = flagAnonimity;
	}

}
