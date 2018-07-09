package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tagroup.fparking.service.domain.Parking;
public interface ParkingRepository extends JpaRepository<Parking, Long>{
	@Query("select p,( 6371 * Acos( Cos( Radians(:mylatitude) ) * Cos( Radians( latitude ) ) * Cos( Radians( longitude ) - Radians(:mylongitude) ) + Sin( Radians(:mylatitude) ) * Sin( Radians( latitude ) ) ) ) AS distance from Parking p where totalspace > currentspace AND status=1 GROUP BY 'distance' HAVING 'distance' <3")
	  List<Parking> findByLatitudeANDLongitude(@Param("mylatitude") String mylatitude,
	                                 @Param("mylongitude") String mylongitude);
	
}
