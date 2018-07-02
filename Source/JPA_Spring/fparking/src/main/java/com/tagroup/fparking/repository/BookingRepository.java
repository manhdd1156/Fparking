package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Booking;
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
