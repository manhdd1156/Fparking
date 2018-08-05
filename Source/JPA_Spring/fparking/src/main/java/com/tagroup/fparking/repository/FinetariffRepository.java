package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Finetariff;
import com.tagroup.fparking.service.domain.Vehicletype;
public interface FinetariffRepository extends JpaRepository<Finetariff, Long>{
public Finetariff findByVehicletype(Vehicletype vehicletype);
}
