package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.PusherService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;
@Service
public class PusherServiceImpl implements PusherService{
//private Pusher pusher;
//@put
	@Override
	public void trigger(String channal, String event, String data) {
		// TODO Auto-generated method stub
//		pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
		
	}


}
