package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Staff;
public interface RatingRepository extends JpaRepository<Rating, Long>{
	List<Rating> findByStaff(Staff staff);
	List<Rating> findByDriver(Driver driver);
}
