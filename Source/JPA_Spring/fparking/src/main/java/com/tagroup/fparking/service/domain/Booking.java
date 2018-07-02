package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double price;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timein;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timeout;
//
//	//bi-directional many-to-one association to Vehicle
//	@ManyToOne
//	@JoinColumn(name = "vehicle_id")
//	private Vehicle vehicle;
//
//	//bi-directional many-to-one association to Parking
//	@ManyToOne
//	@JoinColumn(name = "parking_id")
//	private Parking parking;
//
//	//bi-directional many-to-one association to Transaction
//	@OneToMany(mappedBy="booking")
//	private List<Transaction> transactions;

	public Booking() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimein() {
		return this.timein;
	}

	public void setTimein(Date timein) {
		this.timein = timein;
	}

	public Date getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

//	public Vehicle getVehicle() {
//		return this.vehicle;
//	}
//
//	public void setVehicle(Vehicle vehicle) {
//		this.vehicle = vehicle;
//	}
//
//	public Parking getParking() {
//		return this.parking;
//	}
//
//	public void setParking(Parking parking) {
//		this.parking = parking;
//	}
//
//	public List<Transaction> getTransactions() {
//		return this.transactions;
//	}
//
//	public void setTransactions(List<Transaction> transactions) {
//		this.transactions = transactions;
//	}

//	public Transaction addTransaction(Transaction transaction) {
//		getTransactions().add(transaction);
//		transaction.setBooking(this);
//
//		return transaction;
//	}
//
//	public Transaction removeTransaction(Transaction transaction) {
//		getTransactions().remove(transaction);
//		transaction.setBooking(null);
//
//		return transaction;
//	}

}