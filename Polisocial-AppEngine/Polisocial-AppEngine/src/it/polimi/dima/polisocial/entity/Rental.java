package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;

@Entity
public class Rental extends Post {
	
	
	private Double latitude;
	private Double longitude;
	private Double price;

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
}
