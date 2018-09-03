package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.VehicletypeService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;

@Service
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private VehicletypeService vehicletypeService;
	@Autowired
	private DriverVehicleRepository drivervehicleRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public List<Vehicle> getAll() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();

	}

	@Override
	public Vehicle getById(Long id) throws Exception {
		// TODO Auto-generated method stub

		try {
			return vehicleRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The vehicle was not found");
		}
	}

	@Override
	public DriverVehicle create(DriverVehicleDTO drivervehicle) throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Vehicle v = new Vehicle();
			DriverVehicle dv = new DriverVehicle();
			dv.setDriver(driverRepository.getOne(drivervehicle.getDriverid()));
			Vehicle v1 = findByLicenseplateAndStatus(drivervehicle.getLicenseplate(), 1);
			Vehicle v2 = findByLicenseplateAndStatus(drivervehicle.getLicenseplate(), 0);
			if (v1 != null && v2 == null) { // TH 2
				System.out.println("VehicleServiceImpl/create/TH2 : v1 = " + v1.toString());
				dv.setVehicle(v1);
				dv.setStatus(1);
				dv = drivervehicleRepository.save(dv);
				if (t.getType().equals("STAFF")) {
					Booking bookingUpdate = bookingRepository.getOne(drivervehicle.getId());
					bookingUpdate.setDrivervehicle(dv);
					bookingUpdate = bookingRepository.save(bookingUpdate);
				}
				return dv;
			} else if (v1 == null && v2 != null) { // TH 1
				if (drivervehicle.getColor() != null) {
					v2.setColor(drivervehicle.getColor());
				}
				v2.setStatus(1);
				v2.setVehicletype(vehicletypeService.findByType(drivervehicle.getType()));
				v2 = update(v2);
				System.out.println("VehicleServiceImpl/create/TH1 : v2 = " + v2.toString());
				dv = drivervehicleRepository
						.findByDriverAndVehicle(driverRepository.getOne(drivervehicle.getDriverid()), v2);
				if (dv != null) { // TH4
					System.out.println("VehicleServiceImpl/create/TH4");
					dv.setStatus(1);
					dv = drivervehicleRepository.save(dv);
					if (t.getType().equals("STAFF")) {
						Booking bookingUpdate = bookingRepository.getOne(drivervehicle.getId());
						bookingUpdate.setDrivervehicle(dv);
						bookingUpdate = bookingRepository.save(bookingUpdate);
					}
					return dv;
				} else if (dv == null) { // TH5
					DriverVehicle dvtemp = new DriverVehicle();
					dvtemp.setVehicle(v);
					dvtemp.setDriver(driverRepository.getOne(drivervehicle.getDriverid()));
					dvtemp.setVehicle(v2);
					dvtemp.setStatus(1);
					System.out.println("VehicleServiceImpl/create/TH5 : dv = " + dvtemp.toString());
					dvtemp = drivervehicleRepository.save(dvtemp);
					if (t.getType().equals("STAFF")) {
						Booking bookingUpdate = bookingRepository.getOne(drivervehicle.getId());
						bookingUpdate.setDrivervehicle(dvtemp);
						bookingUpdate = bookingRepository.save(bookingUpdate);
					}
					return dvtemp;
				}
			} else if (v1 == null && v2 == null) { // TH3
				// else v1== null và v2 == null
				v.setLicenseplate(drivervehicle.getLicenseplate());
				if (drivervehicle.getColor() != null) {
					v.setColor(drivervehicle.getColor());
				}
				v.setVehicletype(vehicletypeService.findByType(drivervehicle.getType()));
				v.setStatus(1);
				update(v);

				System.out.println("VehicleServiceImpl/create/TH3 : v = " + v.toString());
				dv.setVehicle(v);
				dv.setDriver(driverRepository.getOne(drivervehicle.getDriverid()));
				dv.setStatus(1);
				dv = drivervehicleRepository.save(dv);
				if (t.getType().equals("STAFF")) {
					Booking bookingUpdate = bookingRepository.getOne(drivervehicle.getId());
					bookingUpdate.setDrivervehicle(dv);
					bookingUpdate = bookingRepository.save(bookingUpdate);
				}
				return dv;
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.CONFLICT, "Something went wrong!");
		}
		return null;

	}

	@Override
	public Vehicle update(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return vehicleRepository.save(vehicle);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vehicle getVehicleByDriverVehicle(Long id) {
		DriverVehicle dv = drivervehicleRepository.getOne(id);
		// TODO Auto-generated method stub
		return dv.getVehicle();
	}

	@Override
	public List<Vehicle> getVehicleByDriver(String phone) {
		// TODO Auto-generated method stub
		List<DriverVehicle> dv = drivervehicleRepository.findByDriver(driverRepository.findByPhone(phone));
		List<Vehicle> listVT = new ArrayList<>();
		for (DriverVehicle driverVehicle : dv) {
			listVT.add(driverVehicle.getVehicle());
		}

		return listVT;
	}

	@Override
	public Vehicle findByLicenseplateAndStatus(String licenseplate, int status) throws Exception {
		// TODO Auto-generated method stub
		try {
			return vehicleRepository.findByLicenseplateAndStatus(licenseplate, status);
		} catch (Exception e) {
			// TODO: handle exception\
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void delete(DriverVehicleDTO drivervehicle) throws Exception {
		// TODO Auto-generated method stub
		try {
			Vehicle v = findByLicenseplateAndStatus(drivervehicle.getLicenseplate(), 1);
			System.out.println("VehicleServiceIml/delete : " + v);

			DriverVehicle dv = drivervehicleRepository
					.findByDriverAndVehicle(driverRepository.getOne(drivervehicle.getDriverid()), v);
			System.out.println("DriverVehicleServiceIml/delete : " + dv);
			dv.setStatus(0);
			drivervehicleRepository.save(dv);
			int vehicleExistCount = 0;
			List<DriverVehicle> dvByvehicleList = drivervehicleRepository.findByVehicle(v);
			for (DriverVehicle dv2 : dvByvehicleList) {
				if (dv2.getStatus() == 1) {
					vehicleExistCount++;
				}
			}
			if (vehicleExistCount == 0) {
				v.setStatus(0);
				update(v);
			}
		} catch (Exception e) {
			throw new APIException(HttpStatus.CONFLICT, "Something went wrong!");
		}
	}

}
