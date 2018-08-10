package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.domain.Owner;

@Service
public class OwnerServiceImpl implements OwnerService {
	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public List<Owner> getAll() {
		// TODO Auto-generated method stub
		return ownerRepository.findAll();

	}

	@Override
	public Owner getById(Long id) throws Exception {
		try {
			return ownerRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}
		// TODO Auto-generated method stub

	}

	@Override
	public Owner create(Owner owner) {
		// TODO Auto-generated method stub
		return ownerRepository.save(owner);

	}

	@Override
	public Owner update(Owner owner) {
		
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			List<Owner> olist = getAll();
			for (Owner o : olist) {
				if (o.getId() == t.getId()) {
					boolean flag = false;
					for (Owner owner2 : olist) {
						if (owner2.getId()!=t.getId() && owner2.getPhone().equals(owner.getPhone())) {
							flag = true;
						}
					}
					if (!flag) {
						owner.setId(t.getId());
						return ownerRepository.save(owner);
					}
					else
						throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");

				}
			}
			
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
		return null;

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Owner owner = ownerRepository.getOne(id);
		ownerRepository.delete(owner);
	}

	@Override
	public Owner findByPhoneAndPassword(String phone, String password) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (ownerRepository.findByPhoneAndPassword(phone, password) != null)
				return ownerRepository.findByPhoneAndPassword(phone, password);
			else
				throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Owner getProfile() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return ownerRepository.getOne(t.getId());
	}

	@Override
	public Owner forgotpassword(Owner owner) throws Exception {
		System.out.println("owner = " + owner);
		Owner o = ownerRepository.findByPhone(owner.getPhone());
		if (o == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Owner was not found");
		}
		o.setPassword(owner.getPassword());
		return ownerRepository.save(o);
	}

}
