package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Rating;
public interface RatingRepository extends JpaRepository<Rating, Long>{
//	List<Rating> findByDriver(Driver driver);
}
