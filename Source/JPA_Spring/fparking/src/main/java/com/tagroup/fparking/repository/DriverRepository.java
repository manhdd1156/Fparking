package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
public interface DriverRepository extends JpaRepository<Driver, Long>{
	public List<Driver> findByStatus(int status);
	public Driver findByPhoneAndPassword(String phone,String password);
	public Driver findByPhone(String phone);
}
