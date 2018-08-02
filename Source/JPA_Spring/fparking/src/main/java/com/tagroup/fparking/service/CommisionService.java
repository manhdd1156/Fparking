package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Commision;


public interface CommisionService {
	public List<Commision> getAll();
	public Commision getById(Long id)throws Exception;
	public Commision create(Commision commision)throws Exception;
	public Commision update(Commision commision)throws Exception;
	public void delete(Long id)throws Exception;
	public double getCommision() throws Exception;
}
