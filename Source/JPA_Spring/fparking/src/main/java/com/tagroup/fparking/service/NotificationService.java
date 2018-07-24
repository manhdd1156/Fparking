package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Notification;

public interface NotificationService {

	public List<Notification> getAll() throws Exception;

	public Notification getById(Long id) throws Exception;

	public Notification create(Notification notification) throws Exception;

	public Notification update(Notification notification) throws Exception;

	public void delete(Long id) throws Exception;
	
}
