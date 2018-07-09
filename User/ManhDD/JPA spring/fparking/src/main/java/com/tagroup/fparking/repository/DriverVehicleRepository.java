package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.DriverVehicle;
public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long>{

}
