package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;



/**
 * The persistent class for the commision database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@NamedQuery(name="Commision.findAll", query="SELECT c FROM Commision c")
public class Commision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private double commision;
	
	public Commision() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getCommision() {
		return this.commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

	@Override
	public String toString() {
		return "Commision [id=" + id + ", commision=" + commision + "]";
	}
	
	
}