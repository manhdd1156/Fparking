package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.FeedbackRepository;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.domain.Feedback;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;
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
