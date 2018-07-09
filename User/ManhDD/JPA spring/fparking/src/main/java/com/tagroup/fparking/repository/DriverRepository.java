package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
public interface DriverRepository extends JpaRepository<Driver, Long>{

}
