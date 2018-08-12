package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	// delete notification by DriverVehicle
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteNotification(@RequestBody Notification notification) throws Exception {

		notificationService.deleteByNoti(notification);
		return new ResponseEntity<>("ok", HttpStatus.OK);

	}

	// get noti when Internet connected 5:48 - 8/11/2018
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "/check", method = RequestMethod.GET)
	public ResponseEntity<?> getNoti() throws Exception {
		System.out.println("NotificationController/check");
		List<Notification> respone = notificationService.check();

		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create new noti
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> createNewNoti(@RequestBody Notification notification) throws Exception {
		System.out.println(notification.toString());
		Notification respone = notificationService.create(notification);

		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// cancel order,checkin,checkout from Parking.
	@PreAuthorize("hasAnyAuthority('STAFF')")
	@RequestMapping(path = "/cancel", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteByStatus(@RequestBody Notification notification) throws Exception {
		System.out.println("notifications/cancel : " + notification.toString());
		Notification respone = notificationService.cancelNoti(notification);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}
}
