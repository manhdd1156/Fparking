package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Finetariff;


public interface FineTariffService {
	public List<Finetariff> getAll();
	public Finetariff getById(Long id)throws Exception;
	public Finetariff create(Finetariff finetariff)throws Exception;
	public Finetariff update(Finetariff finetariff)throws Exception;
	public void delete(Long id)throws Exception;
}
