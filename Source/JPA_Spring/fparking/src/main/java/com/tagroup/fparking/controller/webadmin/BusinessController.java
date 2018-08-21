package com.tagroup.fparking.controller.webadmin;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.CommisionService;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.FineTariffService;
import com.tagroup.fparking.service.VehicletypeService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Commision;
import com.tagroup.fparking.service.domain.Feedback;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Finetariff;
import com.tagroup.fparking.service.domain.Vehicletype;

@Controller
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	private CommisionService commissionService;
	@Autowired
	private VehicletypeService vehicletypeService;
	@Autowired
	private FineTariffService fineTariffService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private FineService fineService;
	@Autowired
	private FeedbackService feedbackService;

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

	// --------------MANAGE COMMISSION-----------
	// get commission
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/commission", method = RequestMethod.GET)
	public String commissionDetail(Map<String, Object> model) throws Exception {
		List<Commision> listCommission;
		try {
			listCommission = commissionService.getAll();
		} catch (Exception e) {
			return "404";
		}
		if (listCommission != null && listCommission.size() > 0) {
			model.put("id", listCommission.get(listCommission.size() - 1).getId());
			double com = listCommission.get(listCommission.size() - 1).getCommision() * 100;
			if (com % 1 != 0) {
				model.put("commission", com);
			} else {
				model.put("commission", (int) com);
			}
		}
		return "commission";
	}

	// edit commission
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/commission", method = RequestMethod.POST)
	public String editCommission(Map<String, Object> model, @RequestParam("commission") String commission)
			throws Exception {

		List<Commision> listCommission;
		Commision comm;
		double commssionTrue = 0;
		try {
			commssionTrue = Double.parseDouble(commission);
			if (commssionTrue > 100) {
				return "404";
			}
		} catch (NumberFormatException e) {
			return "404";
		}
		try {
			listCommission = commissionService.getAll();
		} catch (Exception e) {
			return "404";
		}
		try {
			comm = commissionService.getById(listCommission.get(listCommission.size() - 1).getId());
			comm.setCommision(commssionTrue / 100);
			comm = commissionService.update(comm);
			model.put("id", comm.getId());
			double com = comm.getCommision() * 100;
			if (com % 1 != 0) {
				model.put("commission", com);
			} else {
				model.put("commission", (int) com);
			}
			model.put("messEdit", "Sửa thành công!");
		} catch (Exception e) {
			double com = commssionTrue;
			if (com % 1 != 0) {
				model.put("commission", com);
			} else {
				model.put("commission", (int) com);
			}
			model.put("messError", "Sửa không thành công!");
			return "commission";
		}
		return "commission";
	}

	// -------------MANAGER VEHICLE TYPE-----------------------
	// get vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/vehicletype", method = RequestMethod.GET)
	public String getAllVehicletype(Map<String, Object> model) throws Exception {
		List<Vehicletype> listVehicletype;
		try {
			listVehicletype = vehicletypeService.getAll();
		} catch (Exception e) {
			return "404";
		}
		if (listVehicletype != null && listVehicletype.size() > 0) {
			model.put("listVehicletype", listVehicletype);
			model.put("totalType", listVehicletype.size());
		} else {
			model.put("totalType", 0);
		}

		return "managementvehicletype";
	}

	// go to add form vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/vehicletype/add", method = RequestMethod.GET)
	public String getFormVehicletype(Map<String, Object> model) throws Exception {
		return "addvehicletype";
	}

	// add vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/vehicletype/add", method = RequestMethod.POST)
	public String addVehicletype(Map<String, Object> model, @RequestParam("vehicletype") String vehicletype,
			@RequestParam("priceFine") String priceFine) throws Exception {
		Vehicletype vt = new Vehicletype();
		double vehicletypeTrue = 0;
		double priceFineTrue = 0;
		try {
			vehicletypeTrue = Integer.parseInt(vehicletype);
			priceFineTrue = Double.parseDouble(priceFine);
		} catch (NumberFormatException e) {
			return "404";
		}
		try {
			vt.setType(vehicletype + " chỗ");
			Vehicletype vtCreated = vehicletypeService.create(vt);
			Finetariff finetariff = new Finetariff();
			finetariff.setPrice(priceFineTrue);
			finetariff.setVehicletype(vtCreated);
			try {
				finetariff = fineTariffService.create(finetariff);
			} catch (Exception e) {
				model.put("vehicletype", vehicletype);
				model.put("priceFine", priceFine);
				model.put("messError", "Thêm không thành công!");
				return "addvehicletype";
			}
		} catch (Exception e) {
			return "404";
		}
		return "redirect:/business/vehicletype";
	}

	// go to edit form vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/vehicletype/edit/{id}", method = RequestMethod.GET)
	public String getFormEditVehicletype(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		Vehicletype vehicletype;
		try {
			vehicletype = vehicletypeService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		model.put("type", vehicletype.getType());
		return "editvehicletype";
	}

	// edit vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/vehicletype/edit/{id}", method = RequestMethod.POST)
	public String editVehicletype(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("vehicletype") String typeVehicletype) throws Exception {
		try {
			Vehicletype vehicletype = vehicletypeService.getById(id);
			vehicletype.setType(typeVehicletype);
			try {
				vehicletype = vehicletypeService.update(vehicletype);
				model.put("messSuss", "Sửa thành công!");
				model.put("type", vehicletype.getType());
			} catch (Exception e) {
				model.put("messError", "Sửa không thành công!");
				model.put("type", vehicletype.getType());
				return "editvehicletype";
			}

		} catch (Exception e) {
			return "404";
		}
		return "editvehicletype";
	}

	// ---------------------MANAGE FINE-------------------
	// get all type fine
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/managementfine", method = RequestMethod.GET)
	public String getAllFineType(Map<String, Object> model) throws Exception {
		List<Finetariff> listFineTariff;
		ArrayList<Map<String, Object>> arrayListFineTariff = new ArrayList<>();
		try {
			listFineTariff = fineTariffService.getAll();
			for (Finetariff finetariff : listFineTariff) {
				HashMap<String, Object> m = new HashMap<>();
				double pricefine = finetariff.getPrice();
				m.put("price", currencyVN.format(finetariff.getPrice()));
				m.put("id", finetariff.getId());
				m.put("vehicletype", finetariff.getVehicletype().getType());
				arrayListFineTariff.add(m);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		model.put("arrayListFineTariff", arrayListFineTariff);
		return "managementfine";
	}

	// go to form edit price fine
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/managementfine/edit/{id}", method = RequestMethod.GET)
	public String getFormEditFineType(Map<String, Object> model, @PathVariable("id") long id) throws Exception {
		Finetariff fineTariff;
		try {
			fineTariff = fineTariffService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		model.put("vehicletype", fineTariff.getVehicletype().getType());
		double pricefine = fineTariff.getPrice();
		if (pricefine % 1 == 0) {
			model.put("price", (int) pricefine);
		} else {
			model.put("price", fineTariff.getPrice());
		}
		return "editfine";
	}

	// save price fine
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/managementfine/edit/{id}", method = RequestMethod.POST)
	public String editFineType(Map<String, Object> model, @PathVariable("id") long id,
			@RequestParam("priceFine") Double price) throws Exception {
		Finetariff fineTariff;
		try {
			fineTariff = fineTariffService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		try {
			fineTariff.setPrice(price);
			fineTariff = fineTariffService.update(fineTariff);
		} catch (Exception e) {
			model.put("vehicletype", fineTariff.getVehicletype().getType());
			model.put("price", currencyVN.format(price));
			model.put("messError", "Sủa không thành công!");
			return "editfine";
		}

		Finetariff fineTariff2 = fineTariffService.getById(id);
		model.put("vehicletype", fineTariff.getVehicletype().getType());
		model.put("price", currencyVN.format(fineTariff.getPrice()));
		model.put("messEdit", "Sủa thành công!");
		return "editfine";
	}

	// ---------------MANAGE REVENUE-------------
	// get all revenuve by commission
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/revenue/commission", method = RequestMethod.GET)
	public String getAllRevenueByCommission(Map<String, Object> model,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) throws Exception {

		int check = 0;
		List<Booking> listBooking;
		double revenueCommission = 0;
		ArrayList<Map<String, Object>> arrayListBooking = new ArrayList<>();

		try {
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			return "404";
		}

		if (dateTo == null && dateFrom == null) {
			check = 0;
		} else {
			if (dateFrom.length() > 0 && dateTo.length() == 0) {
				check = 1;
			}
			if (dateFrom.length() == 0 && dateTo.length() > 0)
				check = 2;
			if (dateFrom.length() > 0 && dateTo.length() > 0)
				check = 3;
		}

		switch (check) {
		case 1:
			// get booking
			for (Booking booking : listBooking) {
				HashMap<String, Object> m = new HashMap<>();
				if (booking.getStatus() == 3 && booking.getTimeout() != null
						&& booking.getTimeout().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()) {
					m.put("id", booking.getId());
					String strDate = sdf.format(booking.getTimeout());
					m.put("timeout", strDate);
					m.put("address", booking.getParking().getAddress());
					m.put("amount", currencyVN.format(booking.getAmount()));
					double totalCommission = booking.getComission() * booking.getAmount();
					revenueCommission = revenueCommission + totalCommission;
					m.put("totalCommission", currencyVN.format(totalCommission));
					m.put("city", booking.getParking().getCity().getName());
					arrayListBooking.add(m);
				}
			}
			model.put("revenueCommission", currencyVN.format(revenueCommission));
			model.put("arrayListBooking", arrayListBooking);
			model.put("dateFrom", dateFrom);
			break;
		case 2:
			// get booking
			for (Booking booking : listBooking) {
				HashMap<String, Object> m = new HashMap<>();
				if (booking.getStatus() == 3 && booking.getTimeout() != null
						&& booking.getTimeout().getTime() <= sdf2.parse(dateTo + " 24:00:00").getTime()) {
					m.put("id", booking.getId());
					String strDate = sdf.format(booking.getTimeout());
					m.put("timeout", strDate);
					m.put("address", booking.getParking().getAddress());
					m.put("amount", currencyVN.format(booking.getAmount()));
					double totalCommission = booking.getComission() * booking.getAmount();
					revenueCommission = revenueCommission + totalCommission;
					m.put("totalCommission", currencyVN.format(totalCommission));
					m.put("city", booking.getParking().getCity().getName());
					arrayListBooking.add(m);
				}
			}
			model.put("revenueCommission", currencyVN.format(revenueCommission));
			model.put("arrayListBooking", arrayListBooking);
			model.put("toTalRevenue", "dateTo");
			model.put("dateTo", dateTo);
			break;
		case 3:
			// get booking
			for (Booking booking : listBooking) {
				HashMap<String, Object> m = new HashMap<>();
				if (booking.getStatus() == 3 && booking.getTimeout() != null
						&& booking.getTimeout().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()
						&& booking.getTimeout().getTime() <= sdf2.parse(dateTo + " 24:00:00").getTime()) {
					m.put("id", booking.getId());
					String strDate = sdf.format(booking.getTimeout());
					m.put("timeout", strDate);
					m.put("address", booking.getParking().getAddress());
					m.put("amount", currencyVN.format(booking.getAmount()));
					double totalCommission = booking.getComission() * booking.getAmount();
					revenueCommission = revenueCommission + totalCommission;
					m.put("totalCommission", currencyVN.format(totalCommission));
					m.put("city", booking.getParking().getCity().getName());
					arrayListBooking.add(m);
				}
			}
			model.put("revenueCommission", currencyVN.format(revenueCommission));
			model.put("arrayListBooking", arrayListBooking);
			model.put("dateFrom", dateFrom);
			model.put("dateTo", dateTo);
			break;
		default:
			// get booking
			for (Booking booking : listBooking) {
				HashMap<String, Object> m = new HashMap<>();
				if (booking.getStatus() == 3 && booking.getTimeout() != null) {
					m.put("id", booking.getId());
					m.put("timeout", sdf.format(booking.getTimeout()));
					m.put("address", booking.getParking().getAddress());
					m.put("amount", currencyVN.format(booking.getAmount()));
					double totalCommission = booking.getComission() * booking.getAmount();
					revenueCommission = revenueCommission + totalCommission;
					m.put("totalCommission", currencyVN.format(totalCommission));
					m.put("city", booking.getParking().getCity().getName());
					arrayListBooking.add(m);
				}
			}
			model.put("revenueCommission", currencyVN.format(revenueCommission));
			model.put("arrayListBooking", arrayListBooking);
			break;
		}
		return "managementrevenuebycommission";
	}

	// get all revenuve by fine
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/revenue/fine", method = RequestMethod.GET)
	public String getAllRevenueByFine(Map<String, Object> model,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) throws Exception {

		int check = 0;
		List<Fine> listFine;
		double revenueFine = 0;
		ArrayList<Map<String, Object>> arrayListFine = new ArrayList<>();
		try {
			listFine = fineService.getAll();
		} catch (Exception e) {
			return "404";
		}

		if (dateTo == null && dateFrom == null) {
			check = 0;
		} else {
			if (dateFrom.length() > 0 && dateTo.length() == 0) {
				check = 1;
			}
			if (dateFrom.length() == 0 && dateTo.length() > 0)
				check = 2;
			if (dateFrom.length() > 0 && dateTo.length() > 0)
				check = 3;
		}

		switch (check) {
		case 1:
			// get fine
			for (Fine fine : listFine) {
				HashMap<String, Object> m = new HashMap<>();
				if (fine.getStatus() == 1 && fine.getDate() != null
						&& fine.getDate().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()) {
					m.put("id", fine.getId());
					m.put("dateFine", sdf.format(fine.getDate()));
					m.put("address", fine.getParking().getAddress());
					m.put("licenseplate", fine.getDrivervehicle().getVehicle().getLicenseplate());
					m.put("vehicletype", fine.getDrivervehicle().getVehicle().getVehicletype().getType());
					if (fine.getType() == 0) {
						m.put("objectFine", "Lái xe");
					} else {
						m.put("objectFine", "Bãi xe");
					}

					revenueFine = revenueFine + fine.getPrice();
					m.put("priceFine", currencyVN.format(fine.getPrice()));
					arrayListFine.add(m);
				}
			}
			model.put("revenueFine", currencyVN.format(revenueFine));
			model.put("arrayListFine", arrayListFine);
			model.put("dateFrom", dateFrom);
			break;
		case 2:
			// get fine
			for (Fine fine : listFine) {
				HashMap<String, Object> m = new HashMap<>();
				if (fine.getStatus() == 1 && fine.getDate() != null
						&& fine.getDate().getTime() <= sdf2.parse(dateTo + " 24:00:00").getTime()) {
					m.put("id", fine.getId());
					m.put("dateFine", sdf.format(fine.getDate()));
					m.put("address", fine.getParking().getAddress());
					m.put("licenseplate", fine.getDrivervehicle().getVehicle().getLicenseplate());
					m.put("vehicletype", fine.getDrivervehicle().getVehicle().getVehicletype().getType());
					if (fine.getType() == 0) {
						m.put("objectFine", "Lái xe");
					} else {
						m.put("objectFine", "Bãi xe");
					}

					revenueFine = revenueFine + fine.getPrice();
					m.put("priceFine", currencyVN.format(fine.getPrice()));
					arrayListFine.add(m);
				}
			}
			model.put("revenueFine", currencyVN.format(revenueFine));
			model.put("arrayListFine", arrayListFine);
			model.put("toTalRevenue", "dateTo");
			model.put("dateTo", dateTo);
			break;
		case 3:
			// get fine
			for (Fine fine : listFine) {
				HashMap<String, Object> m = new HashMap<>();
				if (fine.getStatus() == 1 && fine.getDate() != null
						&& fine.getDate().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()
						&& fine.getDate().getTime() <= sdf2.parse(dateTo + " 24:00:00").getTime()) {
					m.put("id", fine.getId());
					m.put("dateFine", sdf.format(fine.getDate()));
					m.put("address", fine.getParking().getAddress());
					m.put("licenseplate", fine.getDrivervehicle().getVehicle().getLicenseplate());
					m.put("vehicletype", fine.getDrivervehicle().getVehicle().getVehicletype().getType());
					if (fine.getType() == 0) {
						m.put("objectFine", "Lái xe");
					} else {
						m.put("objectFine", "Bãi xe");
					}
					revenueFine = revenueFine + fine.getPrice();
					m.put("priceFine", currencyVN.format(fine.getPrice()));
					arrayListFine.add(m);
				}
			}
			model.put("revenueFine", currencyVN.format(revenueFine));
			model.put("arrayListFine", arrayListFine);
			model.put("dateFrom", dateFrom);
			model.put("dateTo", dateTo);
			break;
		default:
			// get fine
			for (Fine fine : listFine) {
				HashMap<String, Object> m = new HashMap<>();
				if (fine.getStatus() == 1 && fine.getDate() != null) {
					m.put("id", fine.getId());
					m.put("dateFine", sdf.format(fine.getDate()));
					m.put("address", fine.getParking().getAddress());
					m.put("licenseplate", fine.getDrivervehicle().getVehicle().getLicenseplate());
					m.put("vehicletype", fine.getDrivervehicle().getVehicle().getVehicletype().getType());
					if (fine.getType() == 0) {
						m.put("objectFine", "Lái xe");
					} else {
						m.put("objectFine", "Bãi xe");
					}
					revenueFine = revenueFine + fine.getPrice();
					m.put("priceFine", currencyVN.format(fine.getPrice()));
					arrayListFine.add(m);
					System.out.println("sizeFineList:" + arrayListFine.size());
				}
			}
			model.put("revenueFine", currencyVN.format(revenueFine));
			model.put("arrayListFine", arrayListFine);
			break;
		}
		return "managementrevenuebyfine";
	}

	// get detail revenue by booking with id
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/revenue/detail/{id}", method = RequestMethod.GET)
	public String getRevenueByID(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		Booking bookingDetail;
		SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss dd-MM-yyyy");
		try {
			bookingDetail = bookingService.getById(id);
			model.put("timein", sdf.format(bookingDetail.getTimein()));
			model.put("timeout", sdf.format(bookingDetail.getTimeout()));
			double totalTime = (bookingDetail.getTimeout().getTime() - bookingDetail.getTimein().getTime())
					/ (60 * 60 * 1000);
			if (totalTime % 1 == 0) {
				model.put("totalTime", (int) totalTime);
			} else {
				model.put("totalTime", (int) totalTime + 1);
			}
			model.put("address", bookingDetail.getParking().getAddress());
			model.put("licenseplate", bookingDetail.getDrivervehicle().getVehicle().getLicenseplate());
			model.put("price", currencyVN.format(bookingDetail.getPrice()));
			model.put("totalFine", currencyVN.format(bookingDetail.getTotalfine()));
			if (bookingDetail.getComission() * 100 % 1 == 0) {
				model.put("commssion", (int) bookingDetail.getComission() * 100);
			} else {
				model.put("commssion", bookingDetail.getComission() * 100);
			}
			model.put("amount", currencyVN.format(bookingDetail.getAmount()));
		} catch (Exception e) {
			return "404";
		}
		return "revenuebyparkingdetail";
	}

	// --------------------MANAGE FEEDBACK--------------------------------------
	// get all feedback
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/feedback", method = RequestMethod.GET)
	public String getAllFeedback(Map<String, Object> model) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Feedback> listFeedback;
		try {
			listFeedback = feedbackService.getAll();
			ArrayList<Map<String, Object>> arrayListFeedback = new ArrayList<>();
			for (Feedback feedback : listFeedback) {
				HashMap<String, Object> m = new HashMap<>();
				if (feedback.getStatus() == 0 || feedback.getStatus() == 1) {
					m.put("id", feedback.getId());
					m.put("dateFeedBack", sdf.format(feedback.getDate()));
					String fb = feedback.getContent();
					if (fb.length() > 30) {
						m.put("content", fb.substring(0, 30) + "...");
					} else {
						m.put("content", fb);
					}

					m.put("nameFeedBack", feedback.getName());
					if (feedback.getType() == 0) {
						m.put("status", "Chưa giải quyết");
					} else {
						m.put("status", "Đã giải quyết");
					}
					arrayListFeedback.add(m);
				}
			}
			model.put("arrayListFeedback", arrayListFeedback);
		} catch (Exception e) {
			return "404";
		}

		return "managementfeedback";

	}

	// view feedback by id
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/feedbackdetail/{id}", method = RequestMethod.GET)
	public String getFeedbackDetail(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		Feedback feedback = new Feedback();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		try {
			feedback = feedbackService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		feedback.setStatus(1);
		try {
			feedbackService.update(feedback);
		} catch (Exception e) {
			return "404";
		}
		model.put("id", id);
		model.put("inforFeedBack", feedback.getName() + "_" + feedback.getPhone());
		model.put("content", feedback.getContent());
		model.put("dateFeedBack", sdf.format(feedback.getDate()));
		model.put("resolve", feedback.getResolve());
		return "viewdetailfeedback";
	}

	// delete feedback
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/feedback/delete/{id}", method = RequestMethod.GET)
	public String deleteFeedback(Map<String, Object> model, @PathVariable("id") Long id) {
		try {
			Feedback feedbackupdate = new Feedback();
			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setStatus(2);
			feedbackService.update(feedbackupdate);
		} catch (Exception e) {
			return "404";
		}
		return "redirect:/business/feedback";
	}

	// resolve feedback
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/feedbackdetail/{id}", method = RequestMethod.POST)
	public String resolveFeedback(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("resolve") String resolve) {
		Feedback feedbackupdate = new Feedback();
		sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		try {
			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setType(1);
			feedbackupdate.setResolve(resolve);
			try {
				feedbackupdate = feedbackService.update(feedbackupdate);

			} catch (Exception e) {
				model.put("id", id);
				model.put("inforFeedBack", feedbackupdate.getName() + "_" + feedbackupdate.getPhone());
				model.put("content", feedbackupdate.getContent());
				model.put("dateFeedBack", sdf.format(feedbackupdate.getDate()));
				model.put("resolve", feedbackupdate.getResolve());
				model.put("type", 2);
				model.put("messError", "Không thể lưu trạng thái!");
				return "viewdetailfeedback";
			}
		} catch (Exception e) {
			return "404";
		}
		model.put("id", id);
		model.put("inforFeedBack", feedbackupdate.getName() + "_" + feedbackupdate.getPhone());
		model.put("content", feedbackupdate.getContent());
		model.put("dateFeedBack", sdf.format(feedbackupdate.getDate()));
		model.put("resolve", feedbackupdate.getResolve());
		model.put("type", 1);
		model.put("messSucc", "Đã giải quyết!");
		return "viewdetailfeedback";
	}

}
