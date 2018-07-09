package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the vehicle database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String licenseplate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
	private Vehicletype vehicletype;

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

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
	}


}