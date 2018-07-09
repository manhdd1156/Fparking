package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Fine;


public interface FineService {
	public List<Fine> getAll();
	public Fine getById(Long id);
	public Fine create(Fine fine);
	public Fine update(Fine fine);
	public void delete(Long id);
}
