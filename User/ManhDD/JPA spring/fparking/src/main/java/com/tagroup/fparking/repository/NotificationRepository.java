package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Long>{
//public Notification findByParkingIDAndTypeAndEventAndStatus(int parkingID, int Type, String event,int status);
}
