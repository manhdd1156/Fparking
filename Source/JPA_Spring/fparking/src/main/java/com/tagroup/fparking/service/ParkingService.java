package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.service.domain.Parking;


public interface ParkingService {
	public List<Parking> getAll();
	public Parking getById(Long id)throws Exception;
	public Parking create(Parking parking)throws Exception;
	public Parking update(Parking parking)throws Exception;
	public void delete(Long id)throws Exception;
	public List<Parking> findByLatitudeANDLongitude(String latitude, String longitude)throws Exception;
	public List<Parking> findSortByLatitudeANDLongitude(String latitude, String longitude)throws Exception;
    public String getRatingByPid(Long parkingId) throws Exception; 
    public ParkingTariffDTO getTariffByPid(Parking parking)throws Exception;
    public List<Parking> getByStatus(int status)throws Exception;
    public List<Parking> getByOwnerID(Long id) throws Exception;
    public Parking changeSpace(Long parkingid, int space);
 }
