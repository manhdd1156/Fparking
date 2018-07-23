package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.VehicletypeRepository;
import com.tagroup.fparking.service.VehicletypeService;
import com.tagroup.fparking.service.domain.Vehicletype;

@Service
public class VehicletypeServiceImpl implements VehicletypeService{
@Autowired
private VehicletypeRepository vehicletypeRepository;

	@Override
	public List<Vehicletype> getAll() {
		// TODO Auto-generated method stub
		return vehicletypeRepository.findAll();
		
	}

	@Override
	public Vehicletype getById(Long id) {
		// TODO Auto-generated method stub
		return vehicletypeRepository.getOne(id);
	}

	@Override
	public Vehicletype create(Vehicletype vehicletype) {
		// TODO Auto-generated method stub
		return vehicletypeRepository.save(vehicletype);
	
	}

	@Override
	public Vehicletype update(Vehicletype vehicletype) {
		// TODO Auto-generated method stub
		return vehicletypeRepository.save(vehicletype);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Vehicletype vehicletype = vehicletypeRepository.getOne(id);
		vehicletypeRepository.delete(vehicletype);
	}
	

}
