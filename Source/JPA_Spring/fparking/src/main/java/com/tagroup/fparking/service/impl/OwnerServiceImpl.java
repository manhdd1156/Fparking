package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.domain.Owner;
@Service
public class OwnerServiceImpl implements OwnerService{
@Autowired
private OwnerRepository ownerRepository;
	@Override
	public List<Owner> getAll() {
		// TODO Auto-generated method stub
		return ownerRepository.findAll();
		
	}

	@Override
	public Owner getById(Long id) {
		// TODO Auto-generated method stub
		return ownerRepository.getOne(id);
	}

	@Override
	public Owner create(Owner owner) {
		// TODO Auto-generated method stub
		return ownerRepository.save(owner);
	
	}

	@Override
	public Owner update(Owner owner) {
		// TODO Auto-generated method stub
		return ownerRepository.save(owner);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Owner owner = ownerRepository.getOne(id);
		ownerRepository.delete(owner);
	}
	

}
