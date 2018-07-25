package com.tagroup.fparking.service.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the vehicle database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String licenseplate;
	
	private String color;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
	private Vehicletype vehicletype;

//	@ManyToMany
//	@JoinTable(name = "driver_vehicle", joinColumns = @JoinColumn(name = "vehicle_id"), inverseJoinColumns = @JoinColumn(name = "driver_id"))
//	private List<Driver> drivers;
	
	public Vehicle() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String getLicenseplate() {
		return this.licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
	}


}