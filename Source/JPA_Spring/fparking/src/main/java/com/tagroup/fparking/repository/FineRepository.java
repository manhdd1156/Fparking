package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Fine;
public interface FineRepository extends JpaRepository<Fine, Long>{

}
