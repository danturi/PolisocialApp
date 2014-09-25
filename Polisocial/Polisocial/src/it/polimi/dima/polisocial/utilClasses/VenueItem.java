package it.polimi.dima.polisocial.utilClasses;

public class VenueItem {

	private String venueId;
	private String name;
	private String coord;
	private String distance;
	private Integer distanceInMeter;
	
	public VenueItem(String id,String name,String coord){
		this.setVenueId(id);
		this.setName(name);
		this.setCoord(coord);
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}


	public String getVenueId() {
		return venueId;
	}



	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}



	public Integer getDistanceInMeter() {
		return distanceInMeter;
	}



	public void setDistanceInMeter(Integer distanceInMeter) {
		this.distanceInMeter = distanceInMeter;
	}
}