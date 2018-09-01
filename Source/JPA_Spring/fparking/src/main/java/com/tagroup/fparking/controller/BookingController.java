package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.dto.BookingDTO;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Notification;

@Controller
@RequestMapping("/api/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ParkingService parkingService;

	// get all bookings
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getall() throws Exception {
		List<Booking> respone = bookingService.getAll();
		
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get booking by booking id
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER','STAFF')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception {
		Booking respone = bookingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	//
	// get booking by status of booking
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getbystatus(@RequestParam("status") int status) throws Exception {
		Booking respone = bookingService.findByStatus(status);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get booking by parking id, drivervehicleid , status ?
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER')")
	@RequestMapping(path = "/drivervehicle", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByPId(@RequestParam("parkingid") Long parkingid,
			@RequestParam("drivervehicleid") Long drivervehicleid, @RequestParam("status") int status)
			throws Exception {

		Booking respone = bookingService.findByParkingIDAndDriverVehicleIDAndStatus(parkingid, drivervehicleid, status);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

//	// get booking by notification.
//	@PreAuthorize("hasAnyAuthority('STAFF')")
//	@RequestMapping(path = "/notifications", method = RequestMethod.PUT)
//	public ResponseEntity<?> getByNotification(@RequestBody Notification noti) throws Exception {
//		// System.out.println("bookings/update/status : " + noti.toString());
//		Booking respone = bookingService.getByNoti(noti);
//		return new ResponseEntity<>(respone, HttpStatus.OK);
//
//	}
	
	
	//delete booking
	@PreAuthorize("hasAnyAuthority('ADMIN','STAFF','DRIVER')")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBooking(@PathVariable Long id) throws Exception {
		bookingService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	// cancel booking from driver.
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER')")
	@RequestMapping(path = "/drivers/cancel", method = RequestMethod.PUT)
	public ResponseEntity<?> cancelBooking(@RequestBody BookingDTO booking) throws Exception {
		// System.out.println("bookings/update/status : " + noti.toString());
		bookingService.cancel(booking);
		return new ResponseEntity<>("OK", HttpStatus.OK);

	}

	// get booking by parking id = ? 
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/parkings/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByPId(@PathVariable Long id) throws Exception {

		List<Booking> respone = bookingService.findByParking(parkingService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}
	// get booking by owner id = ? 
		@PreAuthorize("hasAnyAuthority('ADMIN','OWNER')")
		@RequestMapping(path = "/owners", method = RequestMethod.GET)
		public ResponseEntity<?> getBookingByOId() throws Exception {
					System.out.println("bookings/owners");
			List<Booking> respone = bookingService.findByOwner();
			return new ResponseEntity<>(respone, HttpStatus.OK);

		}
	// get booking by driver id and type ?    type = 1 : lấy ra list booking có status =3, type = 2 : lấy ra booking mới nhất
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
	@RequestMapping(path = "/drivers", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByDriverID(@RequestParam("type") int type) throws Exception {

		List<Booking> respone = bookingService.findByDriverId(type);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create new booking
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER')")
	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody BookingDTO bookingDTO) throws Exception {
		Booking respone = bookingService.create(bookingDTO.getDriverid(), bookingDTO.getVehicleid(),
				bookingDTO.getParkingid(), bookingDTO.getStatus());
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// update booking
	@PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Booking booking) throws Exception {

		Booking respone = bookingService.update(booking);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// // update booking by status booking.
	@PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
	@RequestMapping(path = "/update/status", method = RequestMethod.PUT)
	public ResponseEntity<?> updateByStatus(@RequestBody Notification noti) throws Exception {
		System.out.println("bookings/update/status : " + noti.toString());
		Booking respone = bookingService.updateByStatus(noti);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// getinfo when driver checkout
	@PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
	@RequestMapping(path = "/update/infockbynoti", method = RequestMethod.PUT)
	public ResponseEntity<?> getInfoCheckOut(@RequestBody Notification noti) throws Exception {
		Booking respone = bookingService.getInfoCheckOutByNoti(noti);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

}
