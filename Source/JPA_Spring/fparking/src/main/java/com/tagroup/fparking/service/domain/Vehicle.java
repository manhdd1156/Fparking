package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vehicle database table.
 * 
 */
@Entity
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String licenseplate;
//
//	//bi-directional many-to-one association to Booking
//	@OneToMany(mappedBy="vehicle")
//	private List<Booking> bookings;
//
//	//bi-directional many-to-one association to DriverVehicle
//	@OneToMany(mappedBy="vehicle")
//	private List<DriverVehicle> driverVehicles;
//
//	//bi-directional many-to-one association to Vehicletype
//	@ManyToOne
//	@JoinColumn(name = "vehicletype_id")
//	private Vehicletype vehicletype;

	public Vehicle() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicenseplate() {
		return this.licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}

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
//		booking.setVehicle(this);
//
//		return booking;
//	}
//
//	public Booking removeBooking(Booking booking) {
//		getBookings().remove(booking);
//		booking.setVehicle(null);
//
//		return booking;
//	}
//
//	public List<DriverVehicle> getDriverVehicles() {
//		return this.driverVehicles;
//	}
//
//	public void setDriverVehicles(List<DriverVehicle> driverVehicles) {
//		this.driverVehicles = driverVehicles;
//	}
//
//	public DriverVehicle addDriverVehicle(DriverVehicle driverVehicle) {
//		getDriverVehicles().add(driverVehicle);
//		driverVehicle.setVehicle(this);
//
//		return driverVehicle;
//	}
//
//	public DriverVehicle removeDriverVehicle(DriverVehicle driverVehicle) {
//		getDriverVehicles().remove(driverVehicle);
//		driverVehicle.setVehicle(null);
//
//		return driverVehicle;
//	}
//
//	public Vehicletype getVehicletype() {
//		return this.vehicletype;
//	}
//
//	public void setVehicletype(Vehicletype vehicletype) {
//		this.vehicletype = vehicletype;
//	}

}