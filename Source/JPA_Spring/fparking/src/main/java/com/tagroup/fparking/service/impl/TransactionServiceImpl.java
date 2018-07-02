package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.TransactionRepository;
import com.tagroup.fparking.service.TransactionService;
import com.tagroup.fparking.service.domain.Transaction;
@Service
public class TransactionServiceImpl implements TransactionService{
@Autowired
private TransactionRepository transactionRepository;
	@Override
	public List<Transaction> getAll() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
		
	}

	@Override
	public Transaction getById(Long id) {
		// TODO Auto-generated method stub
		return transactionRepository.getOne(id);
	}

	@Override
	public Transaction create(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionRepository.save(transaction);
	
	}

	@Override
	public Transaction update(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionRepository.save(transaction);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Transaction transaction = transactionRepository.getOne(id);
		transactionRepository.delete(transaction);
	}
	

}
