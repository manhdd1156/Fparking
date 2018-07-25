package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Notification;

@Service
public class DriverVehicleServiceImpl implements DriverVehicleService {
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;

	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	NotificationService notificationService;
	@Override
	public List<DriverVehicle> getAll() {
		// TODO Auto-generated method stub
		return driverVehicleRepository.findAll();

	}

	@Override
	public DriverVehicle getById(Long id) throws Exception {
		// TODO Auto-generated method stub

		try {
			return driverVehicleRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}
	}

	@Override
	public DriverVehicle create(DriverVehicle drivervehicle) {
		// TODO Auto-generated method stub
		return driverVehicleRepository.save(drivervehicle);

	}

	@Override
	public DriverVehicle update(DriverVehicle drivervehicle) {
		// TODO Auto-generated method stub
		return driverVehicleRepository.save(drivervehicle);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		DriverVehicle drivervehicle = driverVehicleRepository.getOne(id);
		driverVehicleRepository.delete(drivervehicle);
	}


	@Override
	public DriverVehicle getInfoDriverVehicle(Long parkingID,String event) throws Exception {
		// TODO Auto-generated method stub
		Notification noti = notificationService.findByParkingIDAndTypeAndEventAndStatus(parkingID, 1, event, 0);
		System.out.println("DriverVehicleImp/getInfoDriverVehicle : " +noti);
		if (noti != null) {
			Long id = (Long) noti.getDrivervehicle_id();
			return driverVehicleRepository.getOne(id);
		}
		return null;
	}

}
