package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.CommisionRepository;
import com.tagroup.fparking.service.CommisionService;
import com.tagroup.fparking.service.domain.Commision;
@Service
public class CommisionServiceImpl implements CommisionService{
@Autowired
private CommisionRepository commisionRepository;
	@Override
	public List<Commision> getAll() {
		// TODO Auto-generated method stub
		return commisionRepository.findAll();
		
	}

	@Override
	public Commision getById(Long id) {
		// TODO Auto-generated method stub
		return commisionRepository.getOne(id);
	}

	@Override
	public Commision create(Commision commision) {
		// TODO Auto-generated method stub
		return commisionRepository.save(commision);
	
	}

	@Override
	public Commision update(Commision commision) {
		// TODO Auto-generated method stub
		return commisionRepository.save(commision);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Commision commision = commisionRepository.getOne(id);
		commisionRepository.delete(commision);
	}

	@Override
	public double getCommision() throws Exception {
		// TODO Auto-generated method stub
		List<Commision> clist = getAll();
		for (Commision commision : clist) {
			System.out.println("commision = " + commision.getCommision());
			return commision.getCommision();
		}
		return 0;
	}
	

}
