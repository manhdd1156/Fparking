package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
@Service
public class BookingServiceImpl implements BookingService{
@Autowired
private BookingRepository bookingRepository;
	@Override
	public List<Booking> getAll() {
		// TODO Auto-generated method stub
		return bookingRepository.findAll();
		
	}

	@Override
	public Booking getById(Long id) {
		// TODO Auto-generated method stub
		return bookingRepository.getOne(id);
	}

	@Override
	public Booking create(Booking booking) {
		// TODO Auto-generated method stub
		return bookingRepository.save(booking);
	
	}

	@Override
	public Booking update(Booking booking) {
		// TODO Auto-generated method stub
		return bookingRepository.save(booking);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Booking booking = bookingRepository.getOne(id);
		bookingRepository.delete(booking);
	}

	@Override
	public List<Booking> findByParking(Parking parking) {
		return bookingRepository.findByParking(parking);
	}

//	@Override
//	public Booking findByParking_id(long id) {
//		// TODO Auto-generated method stub
//		List<Booking> bookings = bookingRepository.findAll();
//		for (Booking booking : bookings) {
//			if(booking.getParking_id()== id) {
//				return booking;
//			}
//		}
//		return new Booking();
//	}



	

}
