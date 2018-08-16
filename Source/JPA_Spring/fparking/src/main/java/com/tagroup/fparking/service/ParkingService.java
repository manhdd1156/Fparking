package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.ParkingDTO;
import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.service.domain.Parking;


public interface ParkingService {
	public List<Parking> getAll();
	public Parking getById(Long id)throws Exception;
	public List<Parking> getByOId()throws Exception;
	public Parking create(ParkingDTO parkingDTO)throws Exception;
	public Parking update(Parking parking)throws Exception;
	public void delete(Long id)throws Exception;
	public List<Parking> findByLatitudeANDLongitude(String latitude, String longitude)throws Exception;
	public List<Parking> findSortByLatitudeANDLongitude(String latitude, String longitude,Long vehicleid)throws Exception;
	public double getFineParkingByTime(Long parkingid, String fromtime,String totime, Long method)throws Exception;

    public String getRatingByPid(Long parkingId) throws Exception; 
    public ParkingTariffDTO getTariffByPid(Parking parking)throws Exception;
    public List<Parking> getByStatus(int status)throws Exception;
    public List<Parking> getByOwnerID(Long id) throws Exception;
    public Parking changeSpace(Long parkingid, int space);
 }
