package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;
@Service
public class BookingServiceImpl implements BookingService{
@Autowired
private BookingRepository bookingRepository;
@Autowired
private TariffRepository tariffRepository;
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
		Booking b= bookingRepository.getOne(booking.getId());
		b.setStatus(booking.getStatus());
		if(booking.getStatus()==2) {
			
			b.setTimein(booking.getTimein());
		}else if(booking.getStatus()==3) {
			b.setTimeout(booking.getTimeout());
		}
		// TODO Auto-generated method stub
		return bookingRepository.save(b);
		
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

	@Override
	public Booking findByStatus(int status) {
		// TODO Auto-generated method stub
		return bookingRepository.findByStatus(status);
	}

	@Override
	public List<Tariff> getTariffByBid(Booking booking) {
		return tariffRepository.findByParking(booking.getParking());
	}




	

}
