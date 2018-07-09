package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Vehicletype;


public interface VehicletypeService {
	public List<Vehicletype> getAll();
	public Vehicletype getById(Long id);
	public Vehicletype create(Vehicletype vehicletype);
	public Vehicletype update(Vehicletype vehicletype);
	public void delete(Long id);
}
