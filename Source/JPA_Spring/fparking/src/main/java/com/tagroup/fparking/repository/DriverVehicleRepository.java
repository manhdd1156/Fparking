package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.DriverVehicle;
public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long>{
	public List<DriverVehicle> findByDriver(Driver driver);
}
