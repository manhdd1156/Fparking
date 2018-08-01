package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Feedback;

public interface FeedbackService {

	public List<Feedback> getAll() throws Exception;

	public Feedback getById(Long id) throws Exception;

	public Feedback create(Feedback feedback) throws Exception;

	public Feedback update(Feedback feedback) throws Exception;

	public void delete(Long id) throws Exception;

}
