package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.FineRepository;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.domain.Fine;
@Service
public class FineServiceImpl implements FineService{
@Autowired
private FineRepository fineRepository;
	@Override
	public List<Fine> getAll() {
		// TODO Auto-generated method stub
		return fineRepository.findAll();
		
	}

	@Override
	public Fine getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			return fineRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}
	}

	@Override
	public Fine create(Fine fine) {
		// TODO Auto-generated method stub
		return fineRepository.save(fine);
	
	}

	@Override
	public Fine update(Fine fine) {
		// TODO Auto-generated method stub
		return fineRepository.save(fine);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Fine fine = fineRepository.getOne(id);
		fineRepository.delete(fine);
	}

	// get fine price of driver when checkin.
	@Override
	public double getPriceByDrivervehicleId(Long id) throws Exception {
		// TODO Auto-generated method stub
		List<Fine> flist = getAll();
		double finePrice = 0;
		for (Fine fine : flist) {
			if(fine.getDrivervehicle().getId()==id && fine.getType()==1 && fine.getStatus()==0) {
				finePrice+=fine.getPrice();
			}
		}
		return finePrice;
	}
}
