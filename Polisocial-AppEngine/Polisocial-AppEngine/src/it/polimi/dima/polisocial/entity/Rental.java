package it.polimi.dima.polisocial.entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Rental extends Post {
	
	
	private Double latitude;
	private Double longitude;
	private Double price;
	private String address;
	private String type;
	private Integer squaredMeter;
	private Date availability;
	private String contact;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSquaredMeter() {
		return squaredMeter;
	}

	public void setSquaredMeter(Integer squaredMeter) {
		this.squaredMeter = squaredMeter;
	}


	public Date getAvailability() {
		return availability;
	}

	public void setAvailability(Date availability) {
		this.availability = availability;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
