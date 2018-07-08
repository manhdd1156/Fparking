package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Fine getById(Long id) {
		// TODO Auto-generated method stub
		return fineRepository.getOne(id);
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
	

}
