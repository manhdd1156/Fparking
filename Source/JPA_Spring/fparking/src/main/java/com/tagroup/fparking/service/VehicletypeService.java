package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Vehicletype;


public interface VehicletypeService {
	public List<Vehicletype> getAll();
	public Vehicletype getById(Long id)throws Exception;
	public Vehicletype create(Vehicletype vehicletype)throws Exception;
	public Vehicletype update(Vehicletype vehicletype)throws Exception;
	public void delete(Long id)throws Exception;
	public Vehicletype findByType(String type);
}
