package com.tagroup.fparking.service.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long drivervehicle_id;
	
	private Long parking_id;
	
	private String event;
	
	private int type;
	
	private int status;

	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getDrivervehicle_id() {
		return drivervehicle_id;
	}

	public void setDrivervehicle_id(Long drivervehicle_id) {
		this.drivervehicle_id = drivervehicle_id;
	}

	public Long getParking_id() {
		return parking_id;
	}

	public void setParking_id(Long parking_id) {
		this.parking_id = parking_id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", drivervehicle_id=" + drivervehicle_id + ", parking_id=" + parking_id
				+ ", event=" + event + ", type=" + type + ", status=" + status + "]";
	}
	
	

}