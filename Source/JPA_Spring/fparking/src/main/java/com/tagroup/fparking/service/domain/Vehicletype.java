package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the vehicletype database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Vehicletype.findAll", query="SELECT v FROM Vehicletype v")
public class Vehicletype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String type;

	public Vehicletype() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}