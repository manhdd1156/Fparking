package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fine database table.
 * 
 */
@Entity
@NamedQuery(name="Fine.findAll", query="SELECT f FROM Fine f")
public class Fine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private int status;
//
//	//bi-directional many-to-one association to Driver
//	@ManyToOne
//	@JoinColumn(name = "driver_id")
//	private Driver driver2;
//
//	//bi-directional many-to-one association to Parking
//	@ManyToOne
//	@JoinColumn(name = "parking_id")
//	private Parking parking;
//
//	//bi-directional many-to-one association to Finetariff
//	@ManyToOne
//	@JoinColumn(name = "finetariff_id")
//	private Finetariff finetariff;

	public Fine() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//
//	public Driver getDriver2() {
//		return driver2;
//	}
//
//	public void setDriver2(Driver driver2) {
//		this.driver2 = driver2;
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
//	public Finetariff getFinetariff() {
//		return this.finetariff;
//	}
//
//	public void setFinetariff(Finetariff finetariff) {
//		this.finetariff = finetariff;
//	}

}