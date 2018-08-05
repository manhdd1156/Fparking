package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.FinetariffRepository;
import com.tagroup.fparking.service.FineTariffService;
import com.tagroup.fparking.service.domain.Finetariff;
@Service
public class FinetariffServiceImpl implements FineTariffService{
@Autowired
private FinetariffRepository finetariffRepository;
	@Override
	public List<Finetariff> getAll() {
		// TODO Auto-generated method stub
		return finetariffRepository.findAll();
		
	}

	@Override
	public Finetariff getById(Long id) {
		// TODO Auto-generated method stub
		return finetariffRepository.getOne(id);
		
	}

	@Override
	public Finetariff create(Finetariff finetariff) {
		// TODO Auto-generated method stub
		return finetariffRepository.save(finetariff);
	
	}

	@Override
	public Finetariff update(Finetariff finetariff) {
		// TODO Auto-generated method stub
		return finetariffRepository.save(finetariff);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Finetariff finetariff = finetariffRepository.getOne(id);
		finetariffRepository.delete(finetariff);
	}
	

}
