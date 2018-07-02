package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Staff;
public interface StaffRepository extends JpaRepository<Staff, Long>{

}
