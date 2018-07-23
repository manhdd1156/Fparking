package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.PusherService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Notification;
import com.tagroup.fparking.service.domain.Parking;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private PusherService pusherService;
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public List<Booking> getAll() {
		// TODO Auto-generated method stub
		pusherService.trigger("channel", "event", "dataa");
		return bookingRepository.findAll();

	}

	@Override
	public Booking getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return bookingRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Booking create(Booking booking) throws Exception {
		// TODO Auto-generated method stub
		try {
			// driverVehicleid, parkingid, status = 5,
			Booking b = bookingRepository.save(booking);
			if (b != null) {
				Notification n = new Notification();
				n.setDrivervehicle_id(b.getDrivervehicle().getId());
				n.setParking_id(b.getParking().getId());
				n.setEvent("order");
				n.setType(1); // 1 : driver gửi cho parking
				n.setStatus(0); // 0 : parking chưa nhận.
				notificationService.create(n);
				pusherService.trigger(b.getParking().getId() + "channel", "order", "");
			}
			return bookingRepository.save(booking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
		

	}

	@Override
	public Booking update(Booking booking) throws Exception {
		try {
			if (booking == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Booking was not content");
			}
			Booking b = bookingRepository.getOne(booking.getId());
			b.setStatus(booking.getStatus());
			if (booking.getStatus() == 2) {

				b.setTimein(booking.getTimein());
			} else if (booking.getStatus() == 3) {
				b.setTimeout(booking.getTimeout());
			}
			// TODO Auto-generated method stub
			return bookingRepository.save(b);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}
	
	@Override
	public Booking updateByStatus(Booking booking) throws Exception {
		try {
//			if (booking == null) {
//				throw new APIException(HttpStatus.NO_CONTENT, "The Booking was not content");
//			}
//			Booking b = bookingRepository.findByParkingAndStatus(booking.getParking(), booking.getStatus());
//			b.setStatus(1);
//			Date date = new Date("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
////			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
//			b.setTimein(date);
////			Notification noti = new Notification()
//			notificationService.update(new)
			// TODO Auto-generated method stub
//			return bookingRepository.save(b);
			return null;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			Booking booking = bookingRepository.getOne(id);
			bookingRepository.delete(booking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public List<Booking> findByParking(Parking parking) throws Exception {

		try {
			return bookingRepository.findByParking(parking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Booking findByStatus(int status) throws Exception {
		// TODO Auto-generated method stub

		try {
			return bookingRepository.findByStatus(status);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public List<Booking> findByDriverPhone(String phone) throws Exception {

		try {
			List<Booking> bAll = bookingRepository.findAll();
			List<Booking> b = new ArrayList<>();
			for (Booking booking : bAll) {
				if (booking.getDrivervehicle().getDriver().getPhone().equals(phone)) {
					b.add(booking);
				}
			}
			return b;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

}
