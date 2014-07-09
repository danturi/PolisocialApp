package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class PrivateLesson extends Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
}
