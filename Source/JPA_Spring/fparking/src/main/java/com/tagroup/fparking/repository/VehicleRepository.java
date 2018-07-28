package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Vehicle;
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
public Vehicle findByLicenseplateAndStatus(String licenseplate,int status);
}
