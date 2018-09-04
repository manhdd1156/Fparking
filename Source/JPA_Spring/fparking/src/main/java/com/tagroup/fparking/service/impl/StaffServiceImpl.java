package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.StaffRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private ParkingService parkingService;

	@Override
	public List<Staff> getAll() {
		// TODO Auto-generated method stub
		return staffRepository.findAll();

	}

	@Override
	public Staff getById(Long id) throws Exception {
		// TODO Auto-generated method stub

		try {
			return staffRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff create(Staff staff) {
		// TODO Auto-generated method stub
		System.out.println("staffServiceImpl/create/ staff = " + staff);
		try {
			List<Staff> slist = getAll();
			for (Staff s : slist) {
				if (s.getPhone().equals(staff.getPhone())) {
					throw new APIException(HttpStatus.CONFLICT, "phone of Staff is Exist");
				} else {
					System.out.println("=======");
					return staffRepository.save(staff);
				}
			}
		} catch (Exception e) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Error");
		}
		return null;

	}

	@Override
	public Staff update(Staff staff) throws Exception {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
//			if (t.getType().equals("STAFF")) {
				System.out.println("staffServiceimp/update staff = " + staff);
				List<Staff> slist = getAll();
				for (Staff s : slist) {
					if (s.getId() == staff.getId()) {
						if(staff.getParking().getId()!=null)
						staff.setParking(parkingService.getById(staff.getParking().getId()));
						System.out.println("staffServiceimp/update staff = " + staff);
						if (staff.getPassword() == null && t.getType().equals("STAFF"))
							staff.setPassword(s.getPassword());
						boolean flag = false;
						for (Staff staff2 : slist) {
							if (staff2.getPhone().equals(staff.getPhone()) && staff2.getId() != staff.getId()) {
								flag = true;
							}
						}
						if (!flag) {
							return staffRepository.save(staff);
						}

					}
				}
//			} else if (t.getType().equals("OWNER")) {
//				List<Staff> slist = getAll();
//				for (Staff s : slist) {
//					if (s.getPhone().equals(staff.getPhone())) {
//						throw new APIException(HttpStatus.CONFLICT, "phone of Staff is Exist");
//					} else {
//						System.out.println("=======");
//						return staffRepository.save(staff);
//					}
//				}
//				return null;
//			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Staff staff = staffRepository.getOne(id);
		staffRepository.delete(staff);
	}

	@Override
	public List<Staff> findByParking(Parking parking) throws Exception {
		// TODO Auto-generated method stub

		try {
			return staffRepository.findByParking(parking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff findByPhoneAndPassword(String phone, String password) throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println(staffRepository.findByPhoneAndPassword(phone, password));
			if (staffRepository.findByPhoneAndPassword(phone, password) != null)
				return staffRepository.findByPhoneAndPassword(phone, password);
			else
				throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		} catch (Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff findByPhone(String phone) throws Exception {
		try {
			return staffRepository.findByPhone(phone);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff getProfile() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return staffRepository.getOne(t.getId());
	}

	// return list staff by Owner id
	@Override
	public List<Staff> findByOwner() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Staff> returnlist = new ArrayList<>();
		List<Staff> slist = getAll();

		for (Staff staff : slist) {
			if (staff.getParking().getOwner().getId() == t.getId())
				returnlist.add(staff);
		}

		// TODO Auto-generated method stub
		return returnlist;
	}

}
