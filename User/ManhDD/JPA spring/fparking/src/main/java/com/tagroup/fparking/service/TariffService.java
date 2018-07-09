package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Tariff;


public interface TariffService {
	public List<Tariff> getAll();
	public Tariff getById(Long id);
	public Tariff create(Tariff tariff);
	public Tariff update(Tariff tariff);
	public void delete(Long id);
}
