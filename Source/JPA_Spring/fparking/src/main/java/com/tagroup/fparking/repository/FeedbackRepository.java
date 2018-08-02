package com.tagroup.fparking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Feedback;
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
//public Notification findByParkingIDAndTypeAndEventAndStatus(int parkingID, int Type, String event,int status);
}
