package com.tagroup.fparking.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.DriverDTO;
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
			throw new APIException(HttpStatus.NOT_FOUND, "The driver was not found");
		}

	}

	@Override
	public Driver create(Driver driver) throws Exception {
		try {
			System.out.println("driver = " + driver);
			List<Driver> dlist = getAll();
			boolean flag = false;
			for (Driver driver2 : dlist) {
				if (driver2.getPhone().equals(driver.getPhone())) {
					flag = true;
				}
			}
			if (!flag) {

				Driver d = new Driver();
				d.setPhone(driver.getPhone());
				d.setName(driver.getName());
				d.setStatus(1);
				d.setPassword(driver.getPassword());
				return driverRepository.save(d);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println(e);
			 throw new APIException(HttpStatus.CONFLICT, "Phone is exist!");
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
	public Driver update(Driver driver) throws Exception{
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			if (t.getType().equals("ADMIN")) {
				return driverRepository.save(driver);
			} else {
				List<Driver> dlist = getAll();
				for (Driver d : dlist) {
					if (d.getId() == t.getId() && d.getPassword().equals(driver.getPassword())) {
						boolean flag = false;
						for (Driver driver2 : dlist) {
							if (driver2.getPhone().equals(driver.getPhone()) && driver2.getId() != driver.getId()) {
								flag = true;
							}
						}
						if (!flag) {
							return driverRepository.save(driver);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.NOT_FOUND, "Cannot update driver");
		}
		return null;

	}

	@Override
	public void delete(Long id) throws Exception  {
		// TODO Auto-generated method stub
		try {
		Driver driver = driverRepository.getOne(id);
		driver.setStatus(0);
		driverRepository.save(driver);
		}catch (Exception e) {
			// TODO: handle exception
			throw new APIException(HttpStatus.NOT_FOUND, "Cannot delete driver");
		}
	}

	@Override
	public List<Driver> getByStatus(int status) throws Exception {
		// TODO Auto-generated method stub

		try {
			List<Driver> listDriver = driverRepository.findByStatus(status);
			return listDriver;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}

	}

	@Override
	public Driver findByPhoneAndPassword(String phone, String password) throws Exception {
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

	@Override
	public Driver changepassword(DriverDTO driver) throws Exception {

		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Driver d = getById(t.getId());
		System.out.println("driver = " + d);
		if (driver.getNewpassword() != null && d.getPassword().equals(driver.getPassword())) {
			d.setPassword(driver.getNewpassword());
			return driverRepository.save(d);
		}
		return null;
	}

	@Override
	public Driver changepasswordotp(DriverDTO driver) throws Exception {

		Driver d = driverRepository.findByPhone(driver.getPhone());
		if (d == null) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}
		d.setPassword(driver.getPassword());
		return driverRepository.save(d);

	}

	@Override
	public Driver block(Driver driver) throws Exception {
		// TODO Auto-generated method stub
		driver.setStatus(0);
		driverRepository.save(driver);
		return null;
	}

	@Override
	public Boolean validateDriver(Driver driver)  {
		try {
			Driver d1 = getById(driver.getId());
			if (d1.getPhone().equals(driver.getPhone())) {
				return true;
			}
			Driver d = driverRepository.findByPhone(driver.getPhone());
			return d==null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

}
