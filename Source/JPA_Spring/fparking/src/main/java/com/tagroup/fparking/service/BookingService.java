package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Notification;
import com.tagroup.fparking.service.domain.Parking;

public interface BookingService {
	public List<Booking> findByParking(Parking parking) throws Exception;

	public List<Booking> getAll() throws Exception;

	public Booking getById(Long id) throws Exception;

	public Booking create(Long drivervid,Long vehicleid, Long parkingid, int status) throws Exception;

	public Booking update(Booking booking) throws Exception;

	public void delete(Long id) throws Exception;

	public List<Booking> findByDriverId(Long id) throws Exception;

	public Booking findByStatus(int status) throws Exception;

	public Booking updateByStatus(Notification noti) throws Exception;
	
	public Booking getInfoCheckOut(Notification noti) throws Exception;
	
	
	public Booking findByParkingIDAndDriverVehicleIDAndStatus(Long parkingid, Long drivervehicleid,int status);
	
	public Booking getByNoti(Notification noti) throws Exception;
	
	
}
