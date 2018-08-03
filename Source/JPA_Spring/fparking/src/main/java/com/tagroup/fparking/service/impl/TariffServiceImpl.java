package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.service.TariffService;
import com.tagroup.fparking.service.domain.Tariff;
@Service
public class TariffServiceImpl implements TariffService{
@Autowired
private TariffRepository tariffRepository;
	@Override
	public List<Tariff> getAll() {
		// TODO Auto-generated method stub
		return tariffRepository.findAll();
		
	}

	@Override
	public Tariff getById(Long id) {
		// TODO Auto-generated method stub
		return tariffRepository.getOne(id);
	}

	@Override
	public Tariff create(Tariff tariff) {
		// TODO Auto-generated method stub
		return tariffRepository.save(tariff);
	
	}

	@Override
	public Tariff update(Tariff tariff) {
		// TODO Auto-generated method stub
		return tariffRepository.save(tariff);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Tariff tariff = tariffRepository.getOne(id);
		tariffRepository.delete(tariff);
	}

	@Override
	public Tariff findByParkingAndVehicletype(Long parkingid, Long vehicleTypeid) {
		// TODO Auto-generated method stub
		List<Tariff> tariffList = tariffRepository.findAll();
		for (Tariff tariff : tariffList) {
			if(tariff.getParking().getId()==parkingid && tariff.getVehicletype().getId()==vehicleTypeid) {
				return tariff;
			}
		}
		return null;
	}
	

}
