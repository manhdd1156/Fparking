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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.dto.ParkingDTO;
import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.service.CityService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.City;
import com.tagroup.fparking.service.domain.Parking;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private CityService cityService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getall() throws Exception {
		List<Parking> respone = parkingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parking by id
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception {
		Parking respone = parkingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parking by owner id
	@PreAuthorize("hasAnyAuthority('OWNER')")
	@RequestMapping(path = "/owners", method = RequestMethod.GET)
	public ResponseEntity<?> getbyOid() throws Exception {
		List<Parking> respone = parkingService.getByOId();
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parkings by latitude and longitude
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(value = "", params = { "latitude", "longitude" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getBarBySimplePathWithExplicitRequestParams(@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude) throws Exception {

		List<Parking> respone = parkingService.findByLatitudeANDLongitude(latitude, longitude);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parkings sort by latitude and longitude
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(value = "/sort", params = { "latitude", "longitude" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getBySortLocation(@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude, @RequestParam("vehicleid") Long vehicleid) throws Exception {

		List<Parking> respone = parkingService.findSortByLatitudeANDLongitude(latitude, longitude, vehicleid);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get fines of parkings by time
	@PreAuthorize("hasAnyAuthority('STAFF','OWNER')")
	@RequestMapping(path = "/time", method = RequestMethod.GET)
	public ResponseEntity<?> getFineParkingByTime(@RequestParam("parkingid") Long parkingid,
			@RequestParam("fromtime") String fromtime, @RequestParam("totime") String totime,
			@RequestParam("method") Long method) throws Exception {
		System.out.println("parkingid =" + parkingid + ",fromtime =<" + fromtime + ">, totime =<" + totime
				+ ">,method= " + method);
		double respone = parkingService.getFineParkingByTime(parkingid, fromtime, totime, method);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	@PreAuthorize("hasAnyAuthority('STAFF','OWNER')")
	@RequestMapping(path = "/testtt", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByDriverID(@RequestParam("type") Long type) throws Exception {

		String respone = "OK";
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get tariff by parking id = ?
	@PreAuthorize("hasAnyAuthority('DRIVER','OWNER')")
	@RequestMapping(path = "/{id}/tariffs", method = RequestMethod.GET)
	public ResponseEntity<?> getTariffByBId(@PathVariable Long id) throws Exception {

		ParkingTariffDTO respone = parkingService.getTariffByPid(parkingService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parking by owner id = ?
	@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
	@RequestMapping(path = "/owner/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getByOwnerId(@PathVariable Long id) throws Exception {

		List<Parking> respone = parkingService.getByOwnerID(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get All city
	@PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
	@RequestMapping(path = "/citys", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCity() throws Exception {

		List<City> respone = cityService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

//	// get Rating by parking id
//	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
//	@RequestMapping(path = "/{id}/rates", method = RequestMethod.GET)
//	public ResponseEntity<?> getRatebyPid(@PathVariable Long id) throws Exception {
//
//		String respone = parkingService.getRatingByPid(id);
//		return new ResponseEntity<>(respone, HttpStatus.OK);
//
//	}

	// create parking
	@PreAuthorize("hasAnyAuthority('OWNER')")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ParkingDTO parkingDTO) throws Exception {

		Parking respone = parkingService.create(parkingDTO);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// update tariff by parking
	@PreAuthorize("hasAnyAuthority('OWNER')")
	@RequestMapping(path = "/tariff/update", method = RequestMethod.GET)
	public ResponseEntity<?> updatetariff(@RequestParam("parkingid") Long parkingid,
			@RequestParam("price9") double price9, @RequestParam("price916") double price916,
			@RequestParam("price1629") double price1629) throws Exception {
		System.out.println("parkingId = " + parkingid + ", price9 = " + price9 + ",price916 = " + price916 + ",price1629 = " + price1629);
		Parking respone = parkingService.updatetariff(parkingid, price9, price916, price1629);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// update parking
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestBody Parking parking) throws Exception {

		Parking respone = parkingService.update(parking);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}
}
