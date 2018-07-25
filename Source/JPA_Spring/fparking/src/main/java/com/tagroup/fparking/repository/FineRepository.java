package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Fine;
public interface FineRepository extends JpaRepository<Fine, Long>{
	public List<Fine> findByDrivervehicle(DriverVehicle drivervehicle);
}
