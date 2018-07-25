package com.tagroup.fparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;
import com.tagroup.fparking.service.domain.Vehicletype;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
	public List<Tariff> findByParking(Parking parking) throws Exception;

	public Tariff findByParkingAndVehicletype(Parking parking, Vehicletype vehicletype) throws Exception;
}
