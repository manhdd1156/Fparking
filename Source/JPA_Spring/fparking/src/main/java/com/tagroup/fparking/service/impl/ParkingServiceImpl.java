	package com.tagroup.fparking.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.dto.TariffSingle;
import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Staff;
import com.tagroup.fparking.service.domain.Tariff;

@Service
public class ParkingServiceImpl implements ParkingService {
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private StaffService staffService;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private TariffRepository tariffRepository;

	@Override
	public List<Parking> getAll() {
		// TODO Auto-generated method stub
		return parkingRepository.findAll();

	}

	@Override
	public Parking getById(Long id) throws Exception {
		// TODO Auto-generated method stub

		try {
			return parkingRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Parking was not found");
		}
	}

	// get parking by Owner ID
	@Override
	public List<Parking> getByOId() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Parking> plist = getAll();
		List<Parking> returnList = new ArrayList<>();
		for (Parking parking : plist) {
			if (parking.getOwner().getId() == t.getId() && parking.getStatus() == 1 || parking.getStatus() == 3) {
				returnList.add(parking);
			}
		}
		return returnList;
	}

	@Override
	public Parking create(Parking parking) {
		// TODO Auto-generated method stub
		return parkingRepository.save(parking);

	}

	@Override
	public Parking update(Parking parking) throws Exception {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Parking p = new Parking();
			if (parking == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "Parking was not content");
			}
			if (t.getType().contains("ADMIN")) {
				return parkingRepository.save(parking);
			} else {
				
				try {
					System.out.println("parkingServiceImpl/update : parking = " + parking);
					Parking temp = parkingRepository.getOne(parking.getId());
					if (parking.getAddress() == null && parking.getCity() == null) { // update space from staff

						temp.setCurrentspace(parking.getCurrentspace());

						parking = temp;
					} else if (parking.getStatus() == 4) { // status = 4 : chờ phê duyệt update

						temp.setCurrentspace(parking.getCurrentspace());
						temp.setTotalspace(parking.getTotalspace());
						temp.setAddress(parking.getAddress());
						temp.setStatus(3);
						temp.setTimeoc(parking.getTimeoc());
						temp.setCity(parking.getCity());
						parking = temp;
					} else if (parking.getStatus() == 5 || parking.getStatus() == 6) { // status = 4 : chờ phê duyệt
																						// block or xóa
						temp.setStatus(parking.getStatus());
						parking = temp;
					}
					p = parkingRepository.save(parking);

				} catch (Exception e) {
					System.out.println("lỗi :" + e);
					// p = parkingRepository.getOne(parking.getId());
					// p.setCurrentspace(parking.getCurrentspace());
					// p = parkingRepository.save(p);
				}
			}
			return p;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Parking was not found");
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Parking parking = parkingRepository.getOne(id);
		parkingRepository.delete(parking);
	}

	@Override
	public List<Parking> findByLatitudeANDLongitude(String latitude, String longitude) throws Exception {
		// TODO Auto-generated method stub
		try {
			List<Parking> returnlist = new ArrayList<>();
			List<Parking> plist = parkingRepository.findByLatitudeANDLongitude(latitude, longitude);
			for (Parking parking : plist) {
				if (parking.getStatus() == 1 && parking.getCurrentspace() > 0) {
					returnlist.add(parking);
				}
			}
			return returnlist;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Parking was not found");
		}

	}

	@Override
	public String getRatingByPid(Long parkingId) throws Exception {
		try {
			Parking p = parkingRepository.getOne(parkingId);
			List<Staff> staffs = staffService.findByParking(parkingRepository.getOne(parkingId));
			double totalPoint = 0;
			double totalRating = 0;
			for (Staff staff : staffs) {
				List<Rating> ratings = ratingRepository.findByStaff(staff);

				for (Rating rating : ratings) {
					if (rating.getType() == 1) {
						totalPoint += rating.getPoint();
						totalRating++;
					}
				}
			}
			// TODO Auto-generated method stub
			return new DecimalFormat("#0.00").format(totalPoint / totalRating);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Rating was not found");
		}
	}

	@Override
	public ParkingTariffDTO getTariffByPid(Parking parking) throws Exception {
		try {
			List<Tariff> tarifflst = tariffRepository.findByParking(parking);
			ParkingTariffDTO ptDTO = new ParkingTariffDTO();
			ptDTO.setParking(parking);
			List<TariffSingle> ts = new ArrayList<>();
			for (Tariff tariff : tarifflst) {
				ts.add(new TariffSingle(tariff.getId(), tariff.getPrice(), tariff.getVehicletype()));
			}
			ptDTO.setTariffList(ts);
			return ptDTO;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Parking was not found");
		}
	}

	// get all acount parking by status 0:1
	@Override
	public List<Parking> getByStatus(int status) {
		// TODO Auto-generated method stub
		try {
			List<Parking> listParking = parkingRepository.findByStatus(status);
			return listParking;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Parking was not found");
		}
	}

	@Override
	public List<Parking> getByOwnerID(Long id) throws Exception {

		Owner o = ownerRepository.getOne(id);

		// TODO Auto-generated method stub
		return parkingRepository.findByOwner(o);
	}

	@Override
	public Parking changeSpace(Long parkingid, int space) {
		Parking p;
		try {
			p = getById(parkingid);
			p.setCurrentspace(space);
			return update(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parking> findSortByLatitudeANDLongitude(String latitude, String longitude) throws Exception {
		// TODO Auto-generated method stub
		List<Parking> plist = getAll();
		Parking ptemp = new Parking();
		List<Parking> returnList = new ArrayList<Parking>();
		for (int i = 0; i < plist.size() - 1; i++) {

			for (int j = i + 1; j < plist.size(); j++) {
				if (Math.abs(Double.parseDouble(plist.get(i).getLatitude()) - Double.parseDouble(latitude)) + (Math
						.abs(Double.parseDouble(plist.get(i).getLongitude()) - Double.parseDouble(longitude))) > Math
								.abs(Double.parseDouble(plist.get(j).getLatitude()) - Double.parseDouble(latitude))
								+ (Math.abs(Double.parseDouble(plist.get(j).getLongitude())
										- Double.parseDouble(longitude)))) {
					ptemp = plist.get(j);
					plist.set(j, plist.get(i));
					plist.set(i, ptemp);

				}
			}

		}
		for (Parking parking : plist) {
			if (Math.abs(Double.parseDouble(parking.getLatitude()) - Double.parseDouble(latitude))
					+ (Math.abs(Double.parseDouble(parking.getLongitude()) - Double.parseDouble(longitude))) < 0.028324
					&& parking.getStatus() == 1 && parking.getCurrentspace() > 0) {
				returnList.add(parking);
			}
		}

		return returnList;
	}

}
