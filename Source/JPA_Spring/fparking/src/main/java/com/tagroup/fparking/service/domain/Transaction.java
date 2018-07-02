package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double totalfine;
//
//	//bi-directional many-to-one association to Booking
//	@ManyToOne
//	@JoinColumn(name = "booking_id")
//	private Booking booking;
//
//	//bi-directional many-to-one association to Commision
//	@ManyToOne
//	@JoinColumn(name = "commision_id")
//	private Commision commision;

	public Transaction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalfine() {
		return this.totalfine;
	}

	public void setTotalfine(double totalfine) {
		this.totalfine = totalfine;
	}
//
//	public Booking getBooking() {
//		return this.booking;
//	}
//
//	public void setBooking(Booking booking) {
//		this.booking = booking;
//	}
//
//	public Commision getCommision() {
//		return this.commision;
//	}
//
//	public void setCommision(Commision commision) {
//		this.commision = commision;
//	}

}