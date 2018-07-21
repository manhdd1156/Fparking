package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.NotificationRepository;
import com.tagroup.fparking.service.NotificationService;
import com.tagroup.fparking.service.domain.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;

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

}
