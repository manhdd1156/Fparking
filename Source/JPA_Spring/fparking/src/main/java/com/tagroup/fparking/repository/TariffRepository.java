package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Tariff;
public interface TariffRepository extends JpaRepository<Tariff, Long>{

}
