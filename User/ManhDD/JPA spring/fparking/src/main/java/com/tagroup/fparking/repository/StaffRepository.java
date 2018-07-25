package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;
public interface StaffRepository extends JpaRepository<Staff, Long>{
	public List<Staff> findByParking(Parking parking)throws Exception;
	public Staff findByPhoneAndPassword(String phone,String password) throws Exception;
	public Staff findByPhone(String phone)throws Exception;
}
