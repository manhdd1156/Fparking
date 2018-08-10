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
	// Commission
	// get commission
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

	@RequestMapping(path = "/commission", method = RequestMethod.GET)
	public String commissiomDetail(Map<String, Object> model) throws Exception {
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
	@RequestMapping(path = "/commission", method = RequestMethod.POST)
	public String saveEditCommission(Map<String, Object> model, @RequestParam("commission") double commission)
			throws Exception {

		List<Commision> listCommission;
		try {
			listCommission = commissionService.getAll();
		} catch (Exception e) {
			return "404";
		}
		Commision comm;
		try {
			comm = commissionService.getById(listCommission.get(listCommission.size() - 1).getId());
		} catch (Exception e) {
			return "404";
		}
		comm.setCommision(commission / 100);
		commissionService.update(comm);
		model.put("messEdit", "Sửa thành công!");

		List<Commision> listCommission2;
		try {
			listCommission2 = commissionService.getAll();
		} catch (Exception e) {
			return "404";
		}
		if (listCommission != null && listCommission2.size() > 0) {
			model.put("id", listCommission2.get(listCommission2.size() - 1).getId());
			double com = listCommission2.get(listCommission2.size() - 1).getCommision() * 100;
			if (com % 1 != 0) {
				model.put("commission", com);
			} else {
				model.put("commission", (int) com);
			}
		}
		return "commission";
	}

	// Vehicle type
	// get vehicletype
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
	@RequestMapping(path = "/vehicletype/add", method = RequestMethod.GET)
	public String addVehicletypeForm(Map<String, Object> model) throws Exception {
		return "addvehicletype";
	}

	// add vehicletype
	@RequestMapping(path = "/vehicletype/add", method = RequestMethod.POST)
	public String addVehicletype(Map<String, Object> model, @RequestParam("vehicletype") Integer vehicletype,
			@RequestParam("priceFine") Double priceFine) throws Exception {
		Vehicletype vt = new Vehicletype();
		vt.setType(vehicletype + " chỗ");
		Vehicletype vtCreated = vehicletypeService.create(vt);

		Finetariff finetariff = new Finetariff();
		finetariff.setPrice(priceFine);
		finetariff.setVehicletype(vtCreated);
		fineTariffService.create(finetariff);

		List<Vehicletype> listVehicletype = vehicletypeService.getAll();
		if (listVehicletype != null && listVehicletype.size() > 0) {
			model.put("listVehicletype", listVehicletype);
			model.put("totalType", listVehicletype.size());
		} else {
			model.put("totalType", 0);
		}

		return "managementvehicletype";
	}

	// go to edit form vehicletype
	@RequestMapping(path = "/vehicletype/edit/{id}", method = RequestMethod.GET)
	public String editVehicletypeForm(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
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
	@RequestMapping(path = "/vehicletype/edit/{id}", method = RequestMethod.POST)
	public String saveVehicletypeForm(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("vehicletype") String typeVehicletype) throws Exception {
		Vehicletype vehicletype = vehicletypeService.getById(id);
		vehicletype.setType(typeVehicletype);
		vehicletypeService.update(vehicletype);
		model.put("messSuss", "Sửa thành công!");

		Vehicletype vehicletype2 = vehicletypeService.getById(id);
		model.put("type", vehicletype2.getType());

		return "editvehicletype";
	}

	// get all type fine
	@RequestMapping(path = "/managementfine", method = RequestMethod.GET)
	public String getAllFineType(Map<String, Object> model) throws Exception {
		List<Finetariff> listFineTariff = fineTariffService.getAll();
		ArrayList<Map<String, Object>> arrayListFineTariff = new ArrayList<>();
		for (Finetariff finetariff : listFineTariff) {
			HashMap<String, Object> m = new HashMap<>();
			double pricefine = finetariff.getPrice();
			m.put("price", currencyVN.format(finetariff.getPrice()));
			m.put("id", finetariff.getId());
			m.put("vehicletype", finetariff.getVehicletype().getType());

			arrayListFineTariff.add(m);
		}

		model.put("arrayListFineTariff", arrayListFineTariff);
		return "managementfine";
	}

	// go to form edit price fine
	@RequestMapping(path = "/managementfine/edit/{id}", method = RequestMethod.GET)
	public String editFineTypeForm(Map<String, Object> model, @PathVariable("id") long id) throws Exception {
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
	@RequestMapping(path = "/managementfine/edit/{id}", method = RequestMethod.POST)
	public String saveFineTypeForm(Map<String, Object> model, @PathVariable("id") long id,
			@RequestParam("priceFine") Double price) throws Exception {
		Finetariff fineTariff;
		try {
			fineTariff = fineTariffService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		fineTariff.setPrice(price);
		fineTariffService.update(fineTariff);

		Finetariff fineTariff2 = fineTariffService.getById(id);
		model.put("vehicletype", fineTariff2.getVehicletype().getType());
		double pricefine = fineTariff2.getPrice();
		if (pricefine % 1 == 0) {
			model.put("price", (int) pricefine);
		} else {
			model.put("price", fineTariff2.getPrice());
		}
		model.put("messEdit", "Sủa thành công!");
		return "editfine";
	}

	// manager Revenue

	// get all revenuve by commission
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
				if (booking.getStatus() == 3
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
				if (booking.getStatus() == 3
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
				if (booking.getStatus() == 3
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
				if (booking.getStatus() == 3) {
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
			break;
		}
		return "managementrevenuebycommission";
	}

	// get all revenuve by fine
	@RequestMapping(path = "/revenue/fine", method = RequestMethod.GET)
	public String getAllRevenueByFine(Map<String, Object> model,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) throws Exception {

		int check = 0;
		List<Fine> listFine;
		double revenueFine = 0;
		ArrayList<Map<String, Object>> arrayListFine = new ArrayList<>();
		ArrayList<Map<String, Object>> arrayListBooking = new ArrayList<>();

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
				if (fine.getStatus() == 1 && fine.getDate().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()) {
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
				if (fine.getStatus() == 1 && fine.getDate().getTime() <= sdf2.parse(dateTo + " 24:00:00").getTime()) {
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
				if (fine.getStatus() == 1 && fine.getDate().getTime() >= sdf2.parse(dateFrom + " 00:00:00").getTime()
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
				if (fine.getStatus() == 1) {
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

	// get detail revenue by booking whit id

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

	// Management FeedBack
	// get all feedback
	@RequestMapping(path = "/feedback", method = RequestMethod.GET)
	public String getAllFeedBack(Map<String, Object> model) throws Exception {
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

	// view feedback
	@RequestMapping(path = "/feedbackdetail/{id}", method = RequestMethod.GET)
	public String getFeedBackDetail(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
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
		return "viewdetailfeedback";
	}

	// delete feedback
	@RequestMapping(path = "/feedback/delete/{id}", method = RequestMethod.GET)
	public String deleteFeedBack(Map<String, Object> model, @PathVariable("id") Long id) {
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
	@RequestMapping(path = "/feedback/resolvefeedback/{id}", method = RequestMethod.GET)
	public String resolveFeedBack(Map<String, Object> model, @PathVariable("id") Long id) {
		try {
			Feedback feedbackupdate = new Feedback();
			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setType(1);
			feedbackService.update(feedbackupdate);
		} catch (Exception e) {
			return "404";
		}
		return "redirect:/business/feedback";
	}

}
