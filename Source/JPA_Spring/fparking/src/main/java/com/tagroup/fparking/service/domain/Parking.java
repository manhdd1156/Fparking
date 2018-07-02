package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the parking database table.
 * 
 */
@Entity
@NamedQuery(name="Parking.findAll", query="SELECT p FROM Parking p")
public class Parking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String address;

	private int currentspace;

	private double deposits;

	private String image;

	private String latitude;

	private String longitude;

	private int status;

	private String timeoc;

	private int totalspace;
//
//	//bi-directional many-to-one association to Booking
//	@OneToMany(mappedBy="parking")
//	private List<Booking> bookings;
//
//	//bi-directional many-to-one association to Fine
//	@OneToMany(mappedBy="parking")
//	private List<Fine> fines;
//
//	//bi-directional many-to-one association to Owner
//	@ManyToOne
//	@JoinColumn(name = "owner_id")
//	private Owner owner;
//
//	//bi-directional many-to-one association to Staff
//	@OneToMany(mappedBy="parking")
//	private List<Staff> staffs;
//
//	//bi-directional many-to-one association to Tariff
//	@OneToMany(mappedBy="parking")
//	private List<Tariff> tariffs;

	public Parking() {
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

	public Object getImage() {
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
//
//	public List<Booking> getBookings() {
//		return this.bookings;
//	}
//
//	public void setBookings(List<Booking> bookings) {
//		this.bookings = bookings;
//	}
//
//	public Booking addBooking(Booking booking) {
//		getBookings().add(booking);
//		booking.setParking(this);
//
//		return booking;
//	}
//
//	public Booking removeBooking(Booking booking) {
//		getBookings().remove(booking);
//		booking.setParking(null);
//
//		return booking;
//	}
//
//	public List<Fine> getFines() {
//		return this.fines;
//	}
//
//	public void setFines(List<Fine> fines) {
//		this.fines = fines;
//	}
//
//	public Fine addFine(Fine fine) {
//		getFines().add(fine);
//		fine.setParking(this);
//
//		return fine;
//	}
//
//	public Fine removeFine(Fine fine) {
//		getFines().remove(fine);
//		fine.setParking(null);
//
//		return fine;
//	}
//
//	public Owner getOwner() {
//		return this.owner;
//	}
//
//	public void setOwner(Owner owner) {
//		this.owner = owner;
//	}
//
//	public List<Staff> getStaffs() {
//		return this.staffs;
//	}
//
//	public void setStaffs(List<Staff> staffs) {
//		this.staffs = staffs;
//	}
//
//	public Staff addStaff(Staff staff) {
//		getStaffs().add(staff);
//		staff.setParking(this);
//
//		return staff;
//	}
//
//	public Staff removeStaff(Staff staff) {
//		getStaffs().remove(staff);
//		staff.setParking(null);
//
//		return staff;
//	}
//
//	public List<Tariff> getTariffs() {
//		return this.tariffs;
//	}
//
//	public void setTariffs(List<Tariff> tariffs) {
//		this.tariffs = tariffs;
//	}
//
//	public Tariff addTariff(Tariff tariff) {
//		getTariffs().add(tariff);
//		tariff.setParking(this);
//
//		return tariff;
//	}
//
//	public Tariff removeTariff(Tariff tariff) {
//		getTariffs().remove(tariff);
//		tariff.setParking(null);
//
//		return tariff;
//	}

}