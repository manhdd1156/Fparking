package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the commision database table.
 * 
 */
@Entity
@NamedQuery(name="Commision.findAll", query="SELECT c FROM Commision c")
public class Commision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double commision;
//
//	//bi-directional many-to-one association to Transaction
//	@OneToMany(mappedBy="commision")
//	private List<Transaction> transactions;

	public Commision() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCommision() {
		return this.commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}
//
//	public List<Transaction> getTransactions() {
//		return this.transactions;
//	}
//
//	public void setTransactions(List<Transaction> transactions) {
//		this.transactions = transactions;
//	}
//
//	public Transaction addTransaction(Transaction transaction) {
//		getTransactions().add(transaction);
//		transaction.setCommision(this);
//
//		return transaction;
//	}
//
//	public Transaction removeTransaction(Transaction transaction) {
//		getTransactions().remove(transaction);
//		transaction.setCommision(null);
//
//		return transaction;
//	}

}