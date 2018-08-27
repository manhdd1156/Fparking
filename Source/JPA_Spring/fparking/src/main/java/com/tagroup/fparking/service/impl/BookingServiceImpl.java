package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.BookingDTO;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.CommisionService;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.FineTariffService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.PusherService;
import com.tagroup.fparking.service.TariffService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Fine;
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
	private NotificationRepository notificationRepository;

	@Autowired
	private TariffService tariffService;
	@Autowired
	private FineService fineService;
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private CommisionService commisionService;
	@Autowired
	private FineTariffService fineTariffService;

	@Override
	public List<Booking> getAll() {
		// TODO Auto-generated method stub
		// pusherService.trigger("channel", "event", "dataa");
		return bookingRepository.findAll();

	}

	@Override
	public Booking getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return bookingRepository.getOne(id);

		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found " + e);
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
				n.setData("");
				Notification nn = notificationService.create(n);
				System.out.println("BookingServiceIml/create noti : " + nn);
				// pusherService.trigger(b.getParking().getId() + "channel", "order", "");
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
			Notification n = new Notification();
			n.setDriver_id(b.getDrivervehicle().getDriver().getId());
			n.setVehicle_id(b.getDrivervehicle().getVehicle().getId());
			n.setParking_id(b.getParking().getId());
			n.setType(1); // 1 : driver gửi cho parking
			n.setStatus(0); // 0 : parking chưa nhận.
			// b.setStatus(booking.getStatus());
			System.out.println("===========================");
			if (booking.getStatus() == 2) {

				n.setEvent("checkin");
				Notification nn = notificationRepository.save(n);
				return updateByStatus(nn);
			} else if (booking.getStatus() == 3) {
				n.setEvent("checkout");
				Notification nn = notificationRepository.save(n);
				return null;
			} else if (booking.getStatus() == 0) {
				b.setStatus(0);
				Fine f = new Fine();
				f.setDrivervehicle(b.getDrivervehicle());
				f.setParking(b.getParking());
				f.setDate(new Date());
				f.setType(1);
				f.setStatus(1);
				f.setPrice(fineTariffService.getByVehicleType(b.getDrivervehicle().getVehicle().getVehicletype())
						.getPrice());
				f = fineService.create(f);
				// parking bị phạt thì trừ luôn vào deposits
				Parking p = parkingService.getById(b.getParking().getId());
				p.setDeposits(p.getDeposits() - f.getPrice());

				b.setStatus(0);
				// tiền cọc < 100 thì ban parking
				if (p.getDeposits() < 100000) {
					p.setStatus(2);
				}
				parkingService.update(p);
				n.setEvent("order");
				n.setType(2);
				n.setData("after");
				Notification nn = notificationRepository.save(n);
				System.out.println("nn.getParking_id = " + nn.getParking_id() + ", " + b.getParking().getCurrentspace());
				parkingService.changeSpace(n.getParking_id(), b.getParking().getCurrentspace() + 1);
				pusherService.trigger(nn.getDriver_id() + "dchannel", "order", "after");
				return bookingRepository.save(b);
			}

			return null;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public Booking getByNoti(Notification noti) throws Exception {
		try {
			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(),noti.getDriver_id(),
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

			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(), noti.getDriver_id(),
					1, noti.getEvent(), 0);

			modelNoti.setType(2);
			Notification n = notificationService.update(modelNoti);
			System.out.println("BookingServerImp/updatebystatus =:  n = " + n.toString());
			List<Booking> blist = bookingRepository.findAll();
			for (Booking booking : blist) {
				if (booking.getParking().getId() == n.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == n.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == n.getVehicle_id()
						&& booking.getStatus() == 5) {
					Parking pTemp = parkingService.getById(n.getParking_id());
					if(pTemp.getTotalspace()<= pTemp.getCurrentspace()) {
						n.setData("cancel");
						n = notificationService.update(n);
						delete(booking.getId());
						
							pusherService.trigger(n.getDriver_id() + "dchannel", n.getEvent(), "cancel");
							return null;
					}
					booking.setStatus(1);
					parkingService.changeSpace(n.getParking_id(), booking.getParking().getCurrentspace() - 1);
					System.out.println("booking = " + booking.toString());
					pusherService.trigger(n.getDriver_id() + "dchannel", n.getEvent(), "ok");
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
					booking.setComission(commisionService.getCommision());

					double finePrice = fineService.getPriceByDrivervehicleId(booking.getDrivervehicle().getDriver().getId());
					booking.setTotalfine(finePrice);
					System.out.println("BookingServerImp/updatebystatus : booking : " + booking);
					double price = tariffService.findByParkingAndVehicletype(n.getParking_id(),
							booking.getDrivervehicle().getVehicle().getVehicletype().getId()).getPrice();
					booking.setPrice(price);
					pusherService.trigger(n.getDriver_id() + "dchannel", n.getEvent(), "ok");
					return bookingRepository.save(booking);
				}
			}
			for (Booking booking : blist) {
				if (booking.getParking().getId() == n.getParking_id()
						&& booking.getDrivervehicle().getDriver().getId() == n.getDriver_id()
						&& booking.getDrivervehicle().getVehicle().getId() == n.getVehicle_id()
						&& booking.getStatus() == 2) {
					fineService.resetFineOfDriver(booking.getDrivervehicle().getDriver().getId()); // reset tiền phạt
					booking.setStatus(3);
					
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
	public Booking getInfoCheckOutByNoti(Notification noti) throws Exception {
		try {
			if (noti == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Notification was not content");
			}
			System.out.println("NOTI = ===  " + noti);
			Notification modelNoti = notificationService.findByParkingIDAndTypeAndEventAndStatus(noti.getParking_id(),noti.getDriver_id(),
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
					totalPrice += booking.getTotalfine();
					booking.setAmount(totalPrice);
					Parking parking = parkingService.getById(booking.getParking().getId());
					parking.setDeposits(parking.getDeposits() - totalPrice * booking.getComission());
					if (parking.getDeposits() < 100000) {
						parking.setStatus(2);
					}
					parkingService.update(parking);
					booking.setStatus(3);
					modelNoti.setType(2);
					modelNoti.setData("ok");
					modelNoti = notificationService.update(modelNoti);
					System.out.println("booking ===: khi chua tahy doi status = 3 : " + booking.toString());
					parkingService.changeSpace(modelNoti.getParking_id(), booking.getParking().getCurrentspace() + 1);
					pusherService.trigger(modelNoti.getDriver_id() + "dchannel", modelNoti.getEvent(), "ok");

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
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	public List<Booking> findByDriverId(int type) throws Exception {
		try {
			List<Booking> b = new ArrayList<>();
			Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (type == 1) {

				List<Booking> bAll = bookingRepository.findAll();

				for (Booking booking : bAll) {
					if (booking.getDrivervehicle().getDriver().getId() == t.getId() && booking.getStatus() == 3) {
						b.add(booking);
					}
				}
			} else if (type == 2) {
				List<Booking> bAll = bookingRepository.findAll();
				List<Booking> btemp = new ArrayList<>();
				for (Booking booking : bAll) {
					if(booking.getDrivervehicle().getDriver().getId()==t.getId()) {
						btemp.add(booking);
					}
				}
				b.add(btemp.get(btemp.size() - 1));
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

	@Override
	public void cancel(BookingDTO bb) throws Exception {
		// TODO Auto-generated method stub
		List<Booking> bAll = bookingRepository.findAll();
		// driver cancel when preorder
		for (Booking booking : bAll) {
			if (booking.getDrivervehicle().getDriver().getId() == bb.getDriverid()
					&& booking.getDrivervehicle().getVehicle().getId() == bb.getVehicleid()
					&& booking.getParking().getId() == bb.getParkingid() && booking.getStatus() == 5) {
				Notification modelNoti = notificationService
						.findByParkingIDAndTypeAndEventAndStatus(booking.getParking().getId(),bb.getDriverid(), 1, "order", 0);
				notificationRepository.delete(modelNoti);
				pusherService.trigger(booking.getParking().getId() + "schannel", "cancel", "preorder");
				bookingRepository.delete(booking);
				return;
			}
		}
		// driver cancel when ordered
		for (Booking booking : bAll) {
			if (booking.getDrivervehicle().getDriver().getId() == bb.getDriverid()
					&& booking.getDrivervehicle().getVehicle().getId() == bb.getVehicleid()
					&& booking.getParking().getId() == bb.getParkingid() && booking.getStatus() == 1) {
				Notification n = new Notification();
				n.setDriver_id(booking.getDrivervehicle().getDriver().getId());
				n.setVehicle_id(booking.getDrivervehicle().getVehicle().getId());
				n.setParking_id(booking.getParking().getId());
				n.setType(1); // 1 : driver gửi cho parking
				n.setStatus(0); // 0 : parking chưa nhận.
				Fine f = new Fine();
				f.setDrivervehicle(booking.getDrivervehicle());
				f.setParking(booking.getParking());
				f.setDate(new Date());
				f.setType(0);
				f.setStatus(0);
				f.setPrice(fineTariffService.getByVehicleType(booking.getDrivervehicle().getVehicle().getVehicletype())
						.getPrice());
				f = fineService.create(f);
				booking.setStatus(0);
				n.setEvent("cancel");
				n.setData("order");
				Notification nn = notificationRepository.save(n);
				parkingService.changeSpace(nn.getParking_id(), booking.getParking().getCurrentspace() + 1);
				pusherService.trigger(nn.getParking_id() + "schannel", "cancel", "order");
				List<Fine> flist = fineService.getByDriverID(booking.getDrivervehicle().getDriver().getId());
				int count = 0;
				for (Fine fine : flist) {
					if (fine.getDrivervehicle().getDriver().getId() == booking.getDrivervehicle().getDriver().getId()
							&& fine.getType() == 0 && fine.getStatus() == 0) {
						count++;
					}
				}
				if (count >=3) {
					driverService.block(booking.getDrivervehicle().getDriver());
				}
				bookingRepository.save(booking);
			}
		}

	}

	@Override
	public List<Booking> findByOwner() throws Exception {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Parking> plist = parkingRepository.findByOwner(ownerRepository.getOne(t.getId()));
		List<Booking> returnBookingList = new ArrayList<>();
		for (Parking parking : plist) {
			List<Booking> bTemp = findByParking(parking);
			for (Booking booking : bTemp) {
				returnBookingList.add(booking);
			}
		}
		return returnBookingList;
	}

}
