package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Owner;


public interface OwnerService {
	public List<Owner> getAll();
	public Owner getById(Long id);
	public Owner create(Owner owner);
	public Owner update(Owner owner);
	public void delete(Long id);
}
