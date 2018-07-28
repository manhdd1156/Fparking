package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.FineRepository;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Notification;
import com.tagroup.fparking.service.domain.Vehicle;

@Service
public class DriverVehicleServiceImpl implements DriverVehicleService {
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private FineRepository fineRepository;

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
	public DriverVehicle update(Long id) {
		
		// TODO Auto-generated method stub
		DriverVehicle dv = driverVehicleRepository.getOne(id);
		dv.setStatus(0);
		return driverVehicleRepository.save(dv);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		DriverVehicle drivervehicle = driverVehicleRepository.getOne(id);
		driverVehicleRepository.delete(drivervehicle);
	}

	@Override
	public List<DriverFineDTO> findByDriverId(Long driverid) throws Exception {
		// TODO Auto-generated method stub
		List<DriverFineDTO> dfList = new ArrayList<>();
		List<Fine> fList = new ArrayList<>();
		List<DriverVehicle> dvList = driverVehicleRepository.findByDriver(driverRepository.getOne(driverid));
		for (DriverVehicle driverVehicle : dvList) {
			List<Fine> fineTemplist = fineRepository.findByDrivervehicle(driverVehicle);
			for (Fine fineTemp : fineTemplist) {
				if (fineTemp.getType() == 0)
					fList.add(fineTemp);
			}
		}
		for (Fine fine : fList) {
			String status = "";
			if (fine.getStatus() == 0) {
				status = "Chưa thu";
			} else {
				status = "Đã thu";
			}
			DriverFineDTO dfDTO = new DriverFineDTO(fine.getDrivervehicle().getVehicle().getLicenseplate(),
					fine.getParking().getAddress(), fine.getDate(), fine.getPrice(), status);
			dfList.add(dfDTO);
		}
		return dfList;
	}
	
	@Override
	public Vehicle getInfoVehicle(Long parkingID,String event) throws Exception {
		// TODO Auto-generated method stub
		Notification noti = notificationService.findByParkingIDAndTypeAndEventAndStatus(parkingID, 1, event, 0);
		System.out.println("DriverVehicleImp/getInfoDriverVehicle : " +noti);
		if (noti != null) {
			Long id = (Long) noti.getVehicle_id();
			return vehicleRepository.getOne(id);
		}
		return null;
	}

	@Override
	public List<DriverVehicle> getDriverVehicleByDriver(Long id) throws Exception {
		// TODO Auto-generated method stub
		List<DriverVehicle> dvlist = driverVehicleRepository.findAll();
		List<DriverVehicle> dvreturn = new ArrayList<>();
		for (DriverVehicle driverVehicle : dvlist) {
			if(driverVehicle.getDriver().getId()==id) {
				dvreturn.add(driverVehicle);
			}
		}
		return dvreturn;
	}

	

}
