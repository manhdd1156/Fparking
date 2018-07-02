package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Transaction;
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
