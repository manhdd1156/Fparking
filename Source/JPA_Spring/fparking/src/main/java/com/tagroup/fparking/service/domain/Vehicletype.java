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
 * The persistent class for the vehicletype database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Vehicletype.findAll", query="SELECT v FROM Vehicletype v")
public class Vehicletype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String type;

	public Vehicletype() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Vehicletype [id=" + id + ", type=" + type + "]";
	}

}