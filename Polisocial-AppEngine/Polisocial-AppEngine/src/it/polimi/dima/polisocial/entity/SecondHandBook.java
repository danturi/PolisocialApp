package it.polimi.dima.polisocial.entity;


import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class SecondHandBook extends Post{

	private String faculty;
	
	private ArrayList<String> authorsBook;
	
	private  String publisher;
	
	private Integer publishedYear;
	
	private String isbn;
	
	private Double price;

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public ArrayList<String> getAuthorsBook() {
		return authorsBook;
	}

	public void setAuthorsBook(ArrayList<String> authorsBook) {
		this.authorsBook = authorsBook;
	}

	public Integer getPublishedDate() {
		return publishedYear;
	}

	public void setPublishedDate(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
