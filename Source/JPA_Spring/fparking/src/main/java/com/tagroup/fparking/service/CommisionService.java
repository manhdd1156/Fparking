package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Commision;


public interface CommisionService {
	public List<Commision> getAll();
	public Commision getById(Long id);
	public Commision create(Commision commision);
	public Commision update(Commision commision);
	public void delete(Long id);
}
