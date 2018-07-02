package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Transaction;


public interface TransactionService {
	public List<Transaction> getAll();
	public Transaction getById(Long id);
	public Transaction create(Transaction transaction);
	public Transaction update(Transaction transaction);
	public void delete(Long id);
}
