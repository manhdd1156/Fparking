package com.tagroup.fparking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
public interface BookingRepository extends JpaRepository<Booking, Long>{
//	@Query(value = "Select * from booking b where b.parking_id = 2",nativeQuery=true)
	public List<Booking> findByParking(Parking parking);
	public Booking findByStatus(int status);
	public Booking findByParkingAndStatus(Parking parking,int status);
}
