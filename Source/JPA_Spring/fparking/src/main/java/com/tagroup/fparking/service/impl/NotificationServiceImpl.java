package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.PusherService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private PusherService pusherService;
	@Autowired
	private BookingService bookingService;

	@Override
	public List<Notification> getAll() {
		// TODO Auto-generated method stub
		return notificationRepository.findAll();

	}

	@Override
	public Notification getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return notificationRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Notification create(Notification notification) throws Exception {
		// TODO Auto-generated method stub
		try {
			pusherService.trigger(notification.getParking_id() + "channel", notification.getEvent(), "");
			
			notification.setData("");
			return notificationRepository.save(notification);

		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public Notification update(Notification notification) throws Exception {
		try {

			// TODO Auto-generated method stub
			return notificationRepository.save(notification);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			Notification notification = notificationRepository.getOne(id);
			notificationRepository.delete(notification);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public Notification findByParkingIDAndTypeAndEventAndStatus(Long parkingID, int type, String event, int status) {
		List<Notification> notilst = notificationRepository.findAll();
		for (Notification notification : notilst) {
			System.out.println(notification.toString());
			System.out.println(parkingID + ",type =" + type + ", event = " + event + ", statuys : " + status);
			if (notification.getParking_id() == parkingID && notification.getType() == type
					&& notification.getEvent().equals(event) && notification.getStatus() == status) {

				return notification;
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notification cancelNoti(Notification notification) throws Exception {
		try {
			if (notification == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "The Booking was not content");
			}
			Notification noti = findByParkingIDAndTypeAndEventAndStatus(notification.getParking_id(), 1,
					notification.getEvent(), 0);
			System.out.println("BookingServerImp/deleteByStatus : " + noti);
			noti.setType(2);
			Notification n = update(noti);
			if (notification.getEvent().equals("order")) {
				List<Booking> blist = bookingService.getAll();
				for (Booking booking : blist) {
					if (booking.getParking().getId() == noti.getParking_id()
							&& booking.getDrivervehicle().getDriver().getId() == noti.getDriver_id()
							&& booking.getDrivervehicle().getVehicle().getId() == noti.getVehicle_id()
							&& booking.getStatus() == 5 && noti.getEvent().equals("order")) {
						bookingService.delete(booking.getId());
						break;
					}
				}
			}
			if (n != null) {
				pusherService.trigger(n.getDriver_id() + "channel", notification.getEvent(), "cancel");
				System.out.println("====================== đã đẩy pusher");
			}

			return n;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}
	}

	@Override
	public void deleteByNoti(Notification notification) throws Exception {
		try {
			System.out.println("NotificationServiceIml/DeleteByNoti :" + notification.toString());
			List<Notification> notilst = notificationRepository.findAll();
			for (Notification noti : notilst) {
				if (noti.getDriver_id() == notification.getDriver_id() && noti.getType() == notification.getType()
						&& noti.getVehicle_id() == notification.getVehicle_id()
						&& noti.getEvent().equals(notification.getEvent())
						&& noti.getStatus() == notification.getStatus()) {
					System.out.println("NotificationServiceIml/DeleteByNoti : ok");
					notificationRepository.delete(noti);
					System.out.println("NotificationServiceIml/DeleteByNoti : ok");
				}
			}

		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Booking was not found");
		}

	}

	@Override
	public List<Notification> check() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Notification> notilst = notificationRepository.findAll();
		for (Notification noti : notilst) {
				if(t.getType().toLowerCase().contains("driver") && noti.getType()==2) {
					pusherService.trigger(noti.getDriver_id() + "dchannel", noti.getEvent(), noti.getData());
				}else if(t.getType().toLowerCase().contains("staff") && noti.getType()==1) {
					pusherService.trigger(noti.getParking_id() + "schannel", noti.getEvent(), noti.getData());
				}
//			if (noti.getDriver_id() == notification.getDriver_id() && noti.getType() == notification.getType()
//					&& noti.getVehicle_id() == notification.getVehicle_id()
//					&& noti.getEvent().equals(notification.getEvent())
//					&& noti.getStatus() == notification.getStatus()) {
//				System.out.println("NotificationServiceIml/DeleteByNoti : ok");
//				notificationRepository.delete(noti);
//				System.out.println("NotificationServiceIml/DeleteByNoti : ok");
//			}
		}
		// TODO Auto-generated method stub
		return null;
	}

}
