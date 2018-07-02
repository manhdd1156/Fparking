package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rating database table.
 * 
 */
@Entity
@NamedQuery(name="Rating.findAll", query="SELECT r FROM Rating r")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="booking_id")
	private int bookingId;

	private int point;

	private int type;

//	//bi-directional many-to-one association to Driver
//	@ManyToOne
//	@JoinColumn(name = "driver_id")
//	private Driver driver;
//
//	//bi-directional many-to-one association to Staff
//	@ManyToOne
//	@JoinColumn(name = "staff_id")
//	private Staff staff;

	public Rating() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getPoint() {
		return this.point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}


	
//
//	public Driver getDriver() {
//		return driver;
//	}
//
//	public void setDriver(Driver driver) {
//		this.driver = driver;
//	}
//
//	public Staff getStaff() {
//		return this.staff;
//	}
//
//	public void setStaff(Staff staff) {
//		this.staff = staff;
//	}

}