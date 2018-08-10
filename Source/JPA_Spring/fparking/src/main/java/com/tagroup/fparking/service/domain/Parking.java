package com.tagroup.fparking.service.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the parking database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Parking.findAll", query="SELECT p FROM Parking p")
public class Parking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String address;

	private int currentspace;

	
	private double deposits;
	@Lob
	private String image;

	private String latitude;

	private String longitude;

	private int status;

	private String timeoc;

	private int totalspace;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private Owner owner;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "id")
	private City city;
	
	public Parking() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCurrentspace() {
		return this.currentspace;
	}

	public void setCurrentspace(int currentspace) {
		this.currentspace = currentspace;
	}

	public double getDeposits() {
		return this.deposits;
	}

	public void setDeposits(double deposits) {
		this.deposits = deposits;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTimeoc() {
		return this.timeoc;
	}

	public void setTimeoc(String timeoc) {
		this.timeoc = timeoc;
	}

	public int getTotalspace() {
		return this.totalspace;
	}

	public void setTotalspace(int totalspace) {
		this.totalspace = totalspace;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", address=" + address + ", currentspace=" + currentspace + ", deposits="
				+ deposits + ", image=" + image + ", latitude=" + latitude + ", longitude=" + longitude + ", status="
				+ status + ", timeoc=" + timeoc + ", totalspace=" + totalspace + ", owner=" + owner + ", city=" + city
				+ "]";
	}
	

}