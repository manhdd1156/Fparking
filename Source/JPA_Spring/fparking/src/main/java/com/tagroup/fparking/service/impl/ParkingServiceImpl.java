	package com.tagroup.fparking.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.ParkingDTO;
import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.dto.TariffSingle;
import com.tagroup.fparking.repository.CityRepository;
import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.TariffService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.City;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Staff;
import com.tagroup.fparking.service.domain.Tariff;
import com.tagroup.fparking.service.domain.Vehicle;
import com.tagroup.fparking.service.domain.Vehicletype;

@Service
public class ParkingServiceImpl implements ParkingService {
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private StaffService staffService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private TariffRepository tariffRepository;
	@Autowired
	private FineService fineService;
	@Autowired
	private CityRepository cityRepository;
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
			if (parking.getOwner().getId() == t.getId()) {
				returnList.add(parking);
			}
		}
		System.out.println("get parking by Owner = " + returnList.size() + " ,... " + returnList);
		return returnList;
	}
	@Override
	public Parking create(ParkingDTO parkingDTO) {
		// TODO Auto-generated method stub
		Token token = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Parking p = new Parking();
		try {
			
		p.setAddress(parkingDTO.getAddress());
		p.setLongitude(parkingDTO.getLongitude());
		p.setLatitude(parkingDTO.getLatitude());
		p.setTimeoc(parkingDTO.getTimeoc());
		p.setTotalspace(parkingDTO.getTotalspace());
		p.setStatus(3);
		p.setCurrentspace(0);
		City c = cityRepository.getOne(parkingDTO.getCity_id());
		p.setCity(c);
		Owner o = ownerRepository.getOne(token.getId());
		p.setOwner(o);
		
		Tariff t = new Tariff();
		System.out.println("parking : " + p);
		p = parkingRepository.save(p);
		System.out.println("parking = : " + p);
		Vehicletype vt = new Vehicletype();
		if(!parkingDTO.getSpace1().equals("")) {
			t.setPrice(Double.parseDouble(parkingDTO.getSpace1()));
			vt.setId((long)1);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff1  = : " + t);
		}
		if(!parkingDTO.getSpace2().equals("")) {
			t = new Tariff();
			t.setPrice(Double.parseDouble(parkingDTO.getSpace2()));
			vt.setId((long)2);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff2  = : " + t);
		}
		
		if(!parkingDTO.getSpace3().equals("")) {
			t = new Tariff();
			t.setPrice(Double.parseDouble(parkingDTO.getSpace3()));
			vt.setId((long)3);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff3  = : " + t);
		}
		System.out.println("tariff  = : " + t);
		
		System.out.println("parking = : " + p);
		}catch(Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.BAD_REQUEST, "Error");
		}
		return p;

	}

	@Override
	public Parking update(Parking parking) throws Exception {
		// TODO Auto-generated method stub
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			System.out.println("parkingServiceImpl/update : parking = " + parking);
			if (parking == null) {
				throw new APIException(HttpStatus.NO_CONTENT, "Parking was not content");
			}
			if (t.getType().contains("ADMIN")) {
				System.out.println("parkingServiceImpl/update ADMIN");
				return parkingRepository.save(parking);
			} else if(t.getType().equals("STAFF")){
				System.out.println("parkingServiceImpl/update STAFF");
				Parking temp = parkingRepository.getOne(parking.getId());
				List<Booking> b = bookingService.findByParking(temp);
				int count = 0;
				for (Booking booking : b) {
					if(booking.getStatus()==1 || booking.getStatus()==2) {
//						System.out.println("count Booking : " + booking);
						count++;
					}
				}
				System.out.println("ParkingServiceIml/Update currentspace = " + parking.getCurrentspace());
				System.out.println("ParkingServiceIml/Update count = " + count);

				if(temp.getTotalspace() - parking.getCurrentspace()<count) {
//					System.out.println("total = " + temp.getTotalspace() + ";current = " + parking.getCurrentspace());
//					System.out.println("ParkingServiceIml/Update count = " + count);
//					System.out.println("====");
					return null;
				}
				temp.setCurrentspace(parking.getCurrentspace());
				parking = temp;
			}else if(t.getType().equals("OWNER")) {
				System.out.println("parkingServiceImpl/update OWNER");
				System.out.println("status = " + parking.getStatus());
				if (parking.getStatus() == 4) { // status = 4 : chờ phê duyệt update
					System.out.println("parkingServiceImpl/update : staus  =   4");
					Parking temp = parkingRepository.getOne(parking.getId());
					temp.setCurrentspace(parking.getCurrentspace());
					temp.setTotalspace(parking.getTotalspace());
					temp.setAddress(parking.getAddress());
					temp.setStatus(4);
					temp.setTimeoc(parking.getTimeoc());
					temp.setCity(parking.getCity());
					parking = temp;
				} else if (parking.getStatus() == 5 || parking.getStatus()==6 ||// status = 5-6 : chờ phê duyệt đóng hoặc xóa
						parking.getStatus()== 1) {    // status = 1 : Hủy đóng hoặc hủy xóa
					System.out.println("parkingServiceImpl/update : staus  =  1 5 6 7 8");
					Parking temp = parkingRepository.getOne(parking.getId());	
					System.out.println("temp parking = " + temp);
					temp.setStatus(parking.getStatus());
					parking = temp;
					System.out.println("parking = " + parking);
				}
			}
				
			return parkingRepository.save(parking);
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
				System.out.println("current space = " + parking.getCurrentspace() +"; " +parking.getAddress());
				if (parking.getStatus() == 1 && parking.getCurrentspace() > 0) {
					System.out.println("current space = " + parking.getCurrentspace());
					System.out.println("xxxxxxxxxxxxxxxxxxxx : " + parking);
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
			System.out.println(p);
			return update(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parking> findSortByLatitudeANDLongitude(String latitude, String longitude,Long vehicleid) throws Exception {
		// TODO Auto-generated method stub
		Vehicle v = vehicleService.getById(vehicleid);
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
				List<Tariff> tlist = tariffRepository.findAll();
				for (Tariff tariff : tlist) {
					if(tariff.getParking().getId()==parking.getId() && tariff.getVehicletype().getId() == v.getVehicletype().getId()) {
						returnList.add(parking);
					}
				}
				
			}
		}
		if(returnList.size()>0) {
		return returnList;
		}
		return null;
	}
			// tìm tổng số tiền bãi xe bị phạt theo thời gian nhập vào
	@Override
	public double getFineParkingByTime(Long parkingid, String fromtime, String totime, Long method) throws Exception {
		double returnprice = 0;
		try {
//		System.out.println("parkingid =" + parkingid + ",fromtime =<" + fromtime + ">, totime =<" + totime +">,method= " + method);
		List<Fine> finelist = fineService.getAll();
		
//		final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		if(method==0) {
			for (Fine fine : finelist) {
				if(fine.getParking().getId()==parkingid) {
					returnprice+=fine.getPrice();
				}
			}
		}else if(method==1 && !fromtime.isEmpty() && totime.isEmpty()) {
			 Long datein = Long.parseLong(fromtime);
			for (Fine fine : finelist) {
				if(fine.getParking().getId()==parkingid && fine.getDate().getTime()>=datein) {
					returnprice+=fine.getPrice();
					
				}
			}
		}else if(method==1 && fromtime.isEmpty() && !totime.isEmpty()) {
			Long dateout = Long.parseLong(fromtime);
			for (Fine fine : finelist) {
				if(fine.getParking().getId()==parkingid && fine.getDate().getTime()<=dateout) {
					returnprice+=fine.getPrice();
					
				}
			}
		}else if(method==1 && !fromtime.isEmpty() && !totime.isEmpty()) {
			Long datein = Long.parseLong(fromtime);
			Long dateout = Long.parseLong(fromtime);
			for (Fine fine : finelist) {
				if(fine.getParking().getId()==parkingid &&fine.getDate().getTime()>=datein && fine.getDate().getTime()<=dateout) {
					returnprice+=fine.getPrice();
					
				}
			}
		}
		
		}catch(Exception e) {
			System.out.println(e);
		}
		return returnprice;
	}

	@Override
	public Parking updatetariff(Long parkingid, double price9, double price916, double price1629) throws Exception {
		// TODO Auto-generated method stub
		Tariff t = new Tariff();
//		System.out.println("parking : " + p);
		Parking p= parkingRepository.getOne(parkingid);
//		System.out.println("parking = : " + p);
		Vehicletype vt = new Vehicletype();
		if(!(price9+"").equals("")) {
			t.setPrice(price9);
			vt.setId((long)1);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff1  = : " + t);
		}
		if(!(price916+"").equals("")) {
			t = new Tariff();
			t.setPrice(price916);
			vt.setId((long)2);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff2  = : " + t);
		}
		
		if((price1629+"").equals("")) {
			t = new Tariff();
			t.setPrice(price1629);
			vt.setId((long)3);
			t.setVehicletype(vt);
			t.setParking(p);
			tariffRepository.save(t);
			System.out.println("tariff3  = : " + t);
		}
		return p;
	}

}
