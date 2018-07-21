package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Tariff;


public interface TariffService {
	public List<Tariff> getAll();
	public Tariff getById(Long id)throws Exception;
	public Tariff create(Tariff tariff)throws Exception;
	public Tariff update(Tariff tariff)throws Exception;
	public void delete(Long id)throws Exception;
}
