package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the owner database table.
 * 
 */
@Entity
@NamedQuery(name="Owner.findAll", query="SELECT o FROM Owner o")
public class Owner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String address;

	private String name;

	private String phone;
//
//	//bi-directional many-to-one association to Parking
//	@OneToMany(mappedBy="owner")
//	private List<Parking> parkings;

	public Owner() {
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
//	public List<Parking> getParkings() {
//		return this.parkings;
//	}
//
//	public void setParkings(List<Parking> parkings) {
//		this.parkings = parkings;
//	}
//
//	public Parking addParking(Parking parking) {
//		getParkings().add(parking);
//		parking.setOwner(this);
//
//		return parking;
//	}
//
//	public Parking removeParking(Parking parking) {
//		getParkings().remove(parking);
//		parking.setOwner(null);
//
//		return parking;
//	}

}