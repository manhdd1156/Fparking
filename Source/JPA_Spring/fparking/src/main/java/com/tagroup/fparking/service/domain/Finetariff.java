package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the finetariff database table.
 * 
 */
@Entity
@NamedQuery(name="Finetariff.findAll", query="SELECT f FROM Finetariff f")
public class Finetariff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double price;

	private int type;
//
//	//bi-directional many-to-one association to Fine
//	@OneToMany(mappedBy="finetariff")
//	private List<Fine> fines;

	public Finetariff() {
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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
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
//		fine.setFinetariff(this);
//
//		return fine;
//	}
//
//	public Fine removeFine(Fine fine) {
//		getFines().remove(fine);
//		fine.setFinetariff(null);
//
//		return fine;
//	}

}