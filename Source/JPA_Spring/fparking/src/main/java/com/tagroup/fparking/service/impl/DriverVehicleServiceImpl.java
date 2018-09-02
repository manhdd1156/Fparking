package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.FineRepository;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.Booking;
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
	private BookingRepository bookingRepository;
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
			throw new APIException(HttpStatus.NOT_FOUND, "The Drivervehicle was not found");
		}
	}

	@Override
	public DriverVehicle create(DriverVehicle drivervehicle) {
		// TODO Auto-generated method stub
		return driverVehicleRepository.save(drivervehicle);

	}

	@Override
	public DriverVehicle updateStatus(Long id) throws Exception{
try {
		DriverVehicle dv = driverVehicleRepository.getOne(id);
		dv.setStatus(0);
		return driverVehicleRepository.save(dv);
}catch (Exception e) {
	throw new APIException(HttpStatus.CONFLICT, "Something went wrong!");
}

	}

	@Override
	public Booking updateDrivervehicleToBooking(DriverVehicleDTO drivervehicleDTO) {
		try {
		System.out.println(drivervehicleDTO);
		DriverVehicle dv = new DriverVehicle();
		dv.setVehicle(vehicleRepository.getOne(drivervehicleDTO.getVehicleid()));
		List<DriverVehicle> dvList = getAll();
		// tìm id trong list drivervehicle để lấy driverid 
		for (DriverVehicle driverVehicle2 : dvList) {
			if(driverVehicle2.getId() == drivervehicleDTO.getId())
			{ 
				dv.setDriver(driverVehicle2.getDriver());
				break;
			}
		}
		for (DriverVehicle driverVehicle2 : dvList) {
			if(driverVehicle2.getDriver() == dv.getDriver() && driverVehicle2.getVehicle() == dv.getVehicle())
			{ 
				dv.setId(driverVehicle2.getId());
				break;
			}
		}
		Booking bookingUpdate = bookingRepository.getOne(drivervehicleDTO.getDriverid());
			bookingUpdate.setDrivervehicle(dv);
			
		return bookingRepository.save(bookingUpdate);
		}catch (Exception e) {
			throw new APIException(HttpStatus.CONFLICT, "Something went wrong");
		}
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
	public DriverVehicle getInfoVehicle(Long parkingID, String event) throws Exception {
		// TODO Auto-generated method stub
		Notification noti = new Notification();
		System.out.println("parkingid = " + parkingID + ", event =" + event);
		// lấy thông tin notification mới nhất
		List<Notification> notiList = notificationService.getAll();
		for (int i = notiList.size() - 1; i >= 0; i--) {
			System.out.println(notiList.get(i));
			if (notiList.get(i).getParking_id() == parkingID && notiList.get(i).getEvent().equals(event)
					&& notiList.get(i).getType() == 1) {
				noti = notiList.get(i);
				System.out.println("lay dc noti");
				break;
			}
		}
		// trả về drivervehicle
		if (noti != null) {

			if (event.equals("cancel")) {
				notificationService.deleteByNoti(new Notification());
			}
			List<DriverVehicle> dvList = getAll();
			for (DriverVehicle driverVehicle : dvList) {
				if (driverVehicle.getDriver().getId() == noti.getDriver_id()
						&& driverVehicle.getVehicle().getId() == noti.getVehicle_id()) {
					System.out.println("lay driverVehicle");
					return driverVehicle;
				}
			}
		}
		return null;
	}

	@Override
	public List<DriverVehicle> getDriverVehicleByDriver(Long id) throws Exception {
		// TODO Auto-generated method stub
		List<DriverVehicle> dvlist = driverVehicleRepository.findAll();
		List<DriverVehicle> dvreturn = new ArrayList<>();
		for (DriverVehicle driverVehicle : dvlist) {
			if (driverVehicle.getDriver().getId() == id && driverVehicle.getStatus() == 1) {
				dvreturn.add(driverVehicle);
			}
		}
		return dvreturn;
	}

}
