package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;

@Entity
public class SecondHandBook extends Post{

	private String faculty;

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
}
