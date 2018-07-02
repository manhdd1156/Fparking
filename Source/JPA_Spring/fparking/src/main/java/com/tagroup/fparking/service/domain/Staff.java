package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the staff database table.
 * 
 */
@Entity
@NamedQuery(name="Staff.findAll", query="SELECT s FROM Staff s")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String address;

	private String name;

	private String phone;

//	//bi-directional many-to-one association to Rating
//	@OneToMany(mappedBy="staff")
//	private List<Rating> ratings;
//
//	//bi-directional many-to-one association to Parking
//	@ManyToOne
//	@JoinColumn(name = "parking_id")
//	private Parking parking;

	public Staff() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
//
//	public List<Rating> getRatings() {
//		return this.ratings;
//	}
//
//	public void setRatings(List<Rating> ratings) {
//		this.ratings = ratings;
//	}
//
//	public Rating addRating(Rating rating) {
//		getRatings().add(rating);
//		rating.setStaff(this);
//
//		return rating;
//	}
//
//	public Rating removeRating(Rating rating) {
//		getRatings().remove(rating);
//		rating.setStaff(null);
//
//		return rating;
//	}
//
//	public Parking getParking() {
//		return this.parking;
//	}
//
//	public void setParking(Parking parking) {
//		this.parking = parking;
//	}

}