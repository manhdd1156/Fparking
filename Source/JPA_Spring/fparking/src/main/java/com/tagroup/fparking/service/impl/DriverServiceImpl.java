package com.tagroup.fparking.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.domain.Driver;

@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepository;

	@Override
	public List<Driver> getAll() {
		// TODO Auto-generated method stub
		return driverRepository.findAll();
	}

	@Override
	public Driver getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return driverRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}

	}

	@Override
	public Driver create(Driver driver) {
		try {
		Driver d = new Driver();
		d.setPhone(driver.getPhone());
		d.setName(driver.getName());
		d.setStatus(1);
			d.setPassword(getMD5Hex(driver.getPassword()));
			return driverRepository.save(d);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
	@Override
	public Driver update(Driver driver) {
		// TODO Auto-generated method stub
		List<Driver> dlist = getAll();
		for (Driver d : dlist) {
			if(d.getId()==driver.getId() && d.getPassword().equals(driver.getPassword())) {
				return driverRepository.save(driver);
			}
		}
		return null;

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Driver driver = driverRepository.getOne(id);
		driverRepository.delete(driver);
	}

	@Override
	public List<Driver> getByStatus(int status) {
		// TODO Auto-generated method stub

		try {
			List<Driver> listDriver = driverRepository.findByStatus(status);
			return listDriver;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}

	}

	@Override
	public Driver findByPhoneAndPassword(String phone, String password) {
		// TODO Auto-generated method stub
		try {
			return driverRepository.findByPhoneAndPassword(phone, password);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}
	}

	@Override
	public Driver getProfile() throws Exception {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return driverRepository.getOne(t.getId());
	}

}
