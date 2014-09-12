package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;

@Entity
public class SecondHandBook extends Post{

	private String faculty;
	
	private String bookTitle;

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
}
