package com.tagroup.fparking.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.FeedbackRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Feedback;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Override
	public List<Feedback> getAll() throws Exception {
		// TODO Auto-generated method stub
		return feedbackRepository.findAll();
	}

	@Override
	public Feedback getById(Long id) {
		// TODO Auto-generated method stub
		return feedbackRepository.getOne(id);
	}

	@Override
	public Feedback create(Feedback feedback) {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Driver d = driverRepository.getOne(t.getId());
		feedback.setName(d.getName());
		feedback.setPhone(d.getPhone());
		feedback.setType(1);
		feedback.setStatus(0);
		Date date = new Date();
		feedback.setDate(date);
		return feedbackRepository.save(feedback);
	
	}

	@Override
	public Feedback update(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackRepository.save(feedback);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Feedback feedback = feedbackRepository.getOne(id);
		feedbackRepository.delete(feedback);
	}


}
