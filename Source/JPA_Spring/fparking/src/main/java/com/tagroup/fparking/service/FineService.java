package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Fine;


public interface FineService {
	public List<Fine> getAll();
	public Fine getById(Long id)throws Exception;
	public Fine create(Fine fine)throws Exception;
	public Fine update(Fine fine)throws Exception;
	public void delete(Long id)throws Exception;
	public double getPriceByDrivervehicleId(Long id) throws Exception;
	public List<Fine> getByDriverID(Long id) throws Exception;
	
}
