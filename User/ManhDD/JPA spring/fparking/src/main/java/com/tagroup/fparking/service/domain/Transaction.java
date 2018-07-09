package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private double totalfine;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "booking_id", referencedColumnName = "id")
	private Booking booking;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commision_id", referencedColumnName = "id")
	private Commision commision;
	
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

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Commision getCommision() {
		return commision;
	}

	public void setCommision(Commision commision) {
		this.commision = commision;
	}
	

}