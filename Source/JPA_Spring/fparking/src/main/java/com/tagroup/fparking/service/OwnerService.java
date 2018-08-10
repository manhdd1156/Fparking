package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Owner;


public interface OwnerService {
	public List<Owner> getAll();
	public Owner getById(Long id)throws Exception;
	public Owner create(Owner owner)throws Exception;
	public Owner update(Owner owner)throws Exception;
	public void delete(Long id)throws Exception;
	public Owner findByPhoneAndPassword(String phone, String password) throws Exception;
	public Owner getProfile() throws Exception;
	public Owner forgotpassword(Owner owner)throws Exception;
}
