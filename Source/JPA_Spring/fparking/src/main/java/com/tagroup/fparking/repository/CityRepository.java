package com.tagroup.fparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {
	
}
