package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.VehicletypeService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;
import com.tagroup.fparking.service.domain.Vehicletype;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private VehicletypeService vehicleTypeService;
	@Autowired
	private DriverVehicleService drivervehicleService;

	
	// get vehicle by drivervehicle_id
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVehicleByDrivervehicle(@PathVariable Long id) throws Exception {
		Vehicle respone = vehicleService.getVehicleByDriverVehicle(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get all vehicles
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVehicle() throws Exception {
		List<Vehicle> respone = vehicleService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get all vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER','STAFF')")
	@RequestMapping(path = "/types", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVehicleType() throws Exception {
		List<Vehicletype> respone = vehicleTypeService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get vehicle by id
		@PreAuthorize("hasAnyAuthority('DRIVER','ADMIN','STAFF')")
		@RequestMapping(path = "/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
			Vehicle respone = vehicleService.getById(id);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		}
		
	// get drivervehicle by driverid
	@PreAuthorize("hasAnyAuthority('DRIVER','ADMIN','STAFF')")
	@RequestMapping(path = "/drivers/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTypesByDriver(@PathVariable Long id) throws Exception {
		List<DriverVehicle> respone = drivervehicleService.getDriverVehicleByDriver(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	
	// get driverVehicle by parking_id and check notification
	@PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
	@RequestMapping(path = "/notifications", method = RequestMethod.GET)
	public ResponseEntity<?> getbydParkingid(@RequestParam("parkingid") Long parkingID,
			@RequestParam("event") String event) throws Exception {
		System.out.println("/api/vehicles/notifications : " + parkingID);
		DriverVehicle respone = drivervehicleService.getInfoVehicle(parkingID, event);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// update status = 0 drivervehicle by drivervehicleid
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER')")
	@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> updateDriverVehicle(@PathVariable Long id) throws Exception {
		DriverVehicle respone = drivervehicleService.updateStatus(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create Vehicle and DriverVehicle by licenseplate, type,color, driverid
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		DriverVehicle respone = vehicleService.create(drivervehicle);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// change car of Driver when checkin
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		System.out.println("drivervehicle = " + drivervehicle);
		Booking respone = drivervehicleService.updateDrivervehicleToBooking(drivervehicle);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// delete Vehicle and DriverVehicle by licenseplate, type, driverid
	@PreAuthorize("hasAnyAuthority('ADMIN','DRIVER')")
	@RequestMapping(path = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		 System.out.println("=======1111");
		vehicleService.delete(drivervehicle);
		return new ResponseEntity<>("ok", HttpStatus.OK);

	}
}
