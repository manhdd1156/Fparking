package com.tagroup.fparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	// delete notification by DriverVehicle
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/drivervehicles", method = RequestMethod.POST)
	public ResponseEntity<?> deleteNotification(@RequestBody DriverVehicle drivervehicle) throws Exception {

//		notificationService.deleteByDriverVehicle(drivervehicle);
		return new ResponseEntity<>("jhhjh",HttpStatus.OK);

	}

	// create new noti
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/bookings", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Booking booking) throws Exception {
		Notification noti = new Notification();
		noti.setType(1);
		noti.setStatus(0);
		noti.setEvent("checkin");
		noti.setDrivervehicle_id(booking.getDrivervehicle().getId());
		noti.setParking_id(booking.getParking().getId());
		Notification respone = notificationService.create(noti);
		
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

}
