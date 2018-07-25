package com.tagroup.fparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tagroup.fparking.service.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	public Admin findByUsernameAndPassword(String username, String password);
}
