package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Staff;
public interface DriverRepository extends JpaRepository<Driver, Long>{
	public Driver findByPhoneAndPassword(String phone,String password);
	public Driver findByPhone(String phone);
}
