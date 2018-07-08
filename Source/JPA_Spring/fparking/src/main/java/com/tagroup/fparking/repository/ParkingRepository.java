package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Parking;
public interface ParkingRepository extends JpaRepository<Parking, Long>{

}
