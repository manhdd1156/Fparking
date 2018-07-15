package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;
public interface StaffRepository extends JpaRepository<Staff, Long>{
	public List<Staff> findByParking(Parking parking);
	public Staff findByPhoneAndPassword(String phone,String password);
}
