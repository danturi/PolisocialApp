package it.polimi.dima.polisocial.entity;


import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class SecondHandBook extends Post{

	private String faculty;
	
	private String bookTitle;
	
	private ArrayList<String> authorsBook;
	
	private  String publisher;
	
	private Date publishedDate;
	
	private String isbn;
	
	private Double price;

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

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
