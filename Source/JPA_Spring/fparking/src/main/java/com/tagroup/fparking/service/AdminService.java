package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Admin;
import com.tagroup.fparking.service.domain.Staff;

public interface AdminService {
	public List<Admin> getAll();

	public Admin getById(Long id) throws Exception;

	public Admin create(Admin tariff) throws Exception;

	public Admin update(Admin tariff) throws Exception;

	public void delete(Long id) throws Exception;

	public Admin checklogin(Admin admin) throws Exception;
}
