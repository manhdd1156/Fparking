package com.tagroup.fparking.dto;

import java.util.List;



import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;


/**
 * The persistent class for the tariff database table.
 * 
 */
public class ParkingDTO {
	private Long id;
	private Long owner_id;
	private Long city_id;
	private String space1;
	private String space2;
	private String space3;
	private String address;
	private String latitude;
	private String longitude;
	private int currentspace;
	private int totalspace;
	private String timeoc;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}
	public Long getCity_id() {
		return city_id;
	}
	
	public String getSpace1() {
		return space1;
	}
	public void setSpace1(String space1) {
		this.space1 = space1;
	}
	public String getSpace2() {
		return space2;
	}
	public void setSpace2(String space2) {
		this.space2 = space2;
	}
	public String getSpace3() {
		return space3;
	}
	public void setSpace3(String space3) {
		this.space3 = space3;
	}
	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public int getCurrentspace() {
		return currentspace;
	}
	public void setCurrentspace(int currentspace) {
		this.currentspace = currentspace;
	}
	public int getTotalspace() {
		return totalspace;
	}
	public void setTotalspace(int totalspace) {
		this.totalspace = totalspace;
	}
	public String getTimeoc() {
		return timeoc;
	}
	public void setTimeoc(String timeoc) {
		this.timeoc = timeoc;
	}
	@Override
	public String toString() {
		return "ParkingDTO [id=" + id + ", owner_id=" + owner_id + ", city_id=" + city_id + ", space1=" + space1
				+ ", space2=" + space2 + ", space3=" + space3 + ", address=" + address + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", currentspace=" + currentspace + ", totalspace=" + totalspace
				+ ", timeoc=" + timeoc + "]";
	}
	

}