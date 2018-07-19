package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Admin;


public interface AdminService {
	public List<Admin> getAll();
	public Admin getById(Long id);
	public Admin create(Admin tariff);
	public Admin update(Admin tariff);
	public void delete(Long id);
	public Admin checklogin(Admin admin);
}
