package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Initiative extends Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String typeOf;
	
	private String title;
	
	public Key getKey() {
		return key;
	}

	public void clearKey() {
		this.key = null;
	}

	public String getTypeOfInitiative() {
		return typeOf;
	}

	public void setTypeOfInitiative(String typeOfInitiative) {
		this.typeOf = typeOfInitiative;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
