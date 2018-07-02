package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Finetariff;


public interface FineTariffService {
	public List<Finetariff> getAll();
	public Finetariff getById(Long id);
	public Finetariff create(Finetariff finetariff);
	public Finetariff update(Finetariff finetariff);
	public void delete(Long id);
}
