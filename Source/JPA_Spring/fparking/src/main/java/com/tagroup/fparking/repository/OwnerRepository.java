package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Owner;
public interface OwnerRepository extends JpaRepository<Owner, Long>{
}
