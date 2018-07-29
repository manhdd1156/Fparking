package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.CommisionRepository;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.PusherService;
import com.tagroup.fparking.service.TariffService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Notification;
import com.tagroup.fparking.service.domain.Parking;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private PusherService pusherService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TariffService tariffService;
	@Autowired
	private FineService fineService;
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private CommisionRepository commisionRepository;

	@Override
	public List<Booking> getAll() {
		// TODO Auto-generated method stub
		pusherService.trigger("channel", "event", "dataa");
		return bookingRepository.findAll();

	}

	@Override
	public Booking getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return bookingRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Booking create(Long driverid, Long vehicleid, Long parkingid, int status) throws Exception {
		// TODO Auto-generated method stub
		try {
			// driverVehicleid, parkingid, status = 5,
			Booking bb = new Booking();
			Parking p = new Parking();

			p.setId(parkingid);
			DriverVehicle dv = driverVehicleRepository.findByDriverAndVehicle(driverRepository.getOne(driverid),
					vehicleRepository.getOne(vehicleid));
			bb.setDrivervehicle(dv);
			bb.setParking(p);
			bb.setStatus(5);

			Booking b = bookingRepository.save(bb);
			System.out.println("BookingServiceIml/Create : bb = " + b.toString());
			if (b != null) {
				Notification n = new Notification();
				n.setDriver_id(driverid);
				n.setVehicle_id(vehicleid);
				n.setParking_id(b.getParking().getId());
				n.setEvent("order");
				n.setType(1); // 1 : driver gửi cho parking
				n.setStatus(0); // 0 : parking chưa nhận.
				Notification nn = notificationService.create(n);
				System.out.println("BookingServiceIml/create noti : " + nn);
				pusherService.trigger(b.getParking().getId() + "channel", "order", "");
			}
			return b;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public Booking update(Booking booking) throws Exception {
		try {
			if (booking == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Booking was not content");
			}
			Booking b = bookingRepository.getOne(booking.getId());
			b.setStatus(booking.getStatus());
			if (booking.getStatus() == 2) {

				b.setTimein(booking.getTimein());
			} else if (booking.getStatus() == 3) {
				b.setTimeout(booking.getTimeout());
			}
			// TODO Auto-generated method stub
			return bookingRepository.save(b);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public Booking getByNoti(Notification noti) throws Exception {
		try {
			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(),
					1, noti.getEvent(), 0);
			List<Booking> blist = bookingRepository.findAll();
			for (Booking booking : blist) {
				if (booking.getParking().getId() == modelNoti.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == modelNoti.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == modelNoti.getVehicle_id()
						&& booking.getStatus() == 2) {
					// booking.setStatus(3);
					System.out.println("booking ===: " + booking.toString());
					// Date date = new Date();
					// booking.setTimeout(date);
					//
					// System.out.println("BookingServerImp/updatebystatus : " + booking);
					// pusherService.trigger(n.getDrivervehicle_id() + "channel", n.getEvent(),
					// "ok");
					return booking;
				}
			}
			// return null;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// update booking, update noti vs type = 2, đẩy pusher về cho driver là ok.
	@Override
	public Booking updateByStatus(Notification noti) throws Exception {
		try {
			if (noti == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Notification was not content");
			}

			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(),
					1, noti.getEvent(), 0);

			modelNoti.setType(2);
			Notification n = notificationService.update(modelNoti);
			System.out.println("BookingServerImp/updatebystatus =: " + n.toString());
			List<Booking> blist = bookingRepository.findAll();
			for (Booking booking : blist) {
				if (booking.getParking().getId() == n.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == n.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == n.getVehicle_id()
						&& booking.getStatus() == 5) {

					booking.setStatus(1);
					System.out.println("booking = " + booking.toString());
					pusherService.trigger(n.getDriver_id() + "channel", n.getEvent(), "ok");
					return bookingRepository.save(booking);
				}
			}

			for (Booking booking : blist) {
				if (booking.getParking().getId() == n.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == n.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == n.getVehicle_id()
						&& booking.getStatus() == 1) {
					booking.setStatus(2);
					System.out.println("booking ==: " + booking.toString());
					Date date = new Date();
					booking.setTimein(date);
					booking.setComission(commisionRepository.getOne((long) 1).getCommision());
					double finePrice = fineService.getPriceByDrivervehicleId(booking.getDrivervehicle().getId());
					booking.setTotalfine(finePrice);
					System.out.println("BookingServerImp/updatebystatus : " + booking);
					double price = tariffService.findByParkingAndVehicletype(n.getParking_id(),
							booking.getDrivervehicle().getVehicle().getVehicletype().getId()).getPrice();
					booking.setPrice(price);
					pusherService.trigger(n.getDriver_id() + "channel", n.getEvent(), "ok");
					return bookingRepository.save(booking);
				}
			}
			for (Booking booking : blist) {
				if (booking.getParking().getId() == n.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == n.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == n.getVehicle_id()
						&& booking.getStatus() == 2) {
					booking.setStatus(3);
					System.out.println("booking ===: " + booking.toString());
					pusherService.trigger(n.getDriver_id() + "channel", n.getEvent(), "ok");
					return bookingRepository.save(booking);
				}
			}

			// TODO Auto-generated method stub

			return null;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	// get thong tin khi driver checkout
	@Override
	public Booking getInfoCheckOut(Notification noti) throws Exception {
		try {
			if (noti == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Notification was not content");
			}

			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(),
					1, noti.getEvent(), 0);
			List<Booking> blist = bookingRepository.findAll();

			for (Booking booking : blist) {
				if (booking.getParking().getId() == modelNoti.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == modelNoti.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == modelNoti.getVehicle_id()
						&& booking.getStatus() == 2) {

					Date date = new Date();
					booking.setTimeout(date);
					long diff = booking.getTimeout().getTime() - booking.getTimein().getTime();
					double diffInHours = diff / ((double) 1000 * 60 * 60);
					double totalPrice = diffInHours * booking.getPrice();
					if (diffInHours < 1) {
						totalPrice = booking.getPrice();
					}
					totalPrice+=booking.getTotalfine();
					booking.setAmount(totalPrice);
					System.out.println("booking ===: khi chua tahy doi status = 3 : " + booking.toString());
					return bookingRepository.save(booking);
				}
			}

			// TODO Auto-generated method stub

			return null;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			Booking booking = bookingRepository.getOne(id);
			bookingRepository.delete(booking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public List<Booking> findByParking(Parking parking) throws Exception {

		try {
			return bookingRepository.findByParking(parking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Booking findByStatus(int status) throws Exception {
		// TODO Auto-generated method stub

		try {
			return bookingRepository.findByStatus(status);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public List<Booking> findByDriverId(Long id) throws Exception {

		try {
			List<Booking> bAll = bookingRepository.findAll();
			List<Booking> b = new ArrayList<>();
			for (Booking booking : bAll) {
				if (booking.getDrivervehicle().getDriver().getId() == id) {
					b.add(booking);
				}
			}
			return b;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public Booking findByParkingIDAndDriverVehicleIDAndStatus(Long parkingid, Long drivervehicleid, int status) {
		// TODO Auto-generated method stub
		List<Booking> bAll = bookingRepository.findAll();

		for (Booking booking : bAll) {
			if (booking.getDrivervehicle().getId() == drivervehicleid && booking.getParking().getId() == parkingid
					&& booking.getStatus() == status) {
				return booking;
			}
		}
		return null;
	}

}
