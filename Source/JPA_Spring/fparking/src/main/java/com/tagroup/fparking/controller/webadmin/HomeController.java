package com.tagroup.fparking.controller.webadmin;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.service.AdminService;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Admin;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Feedback;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Parking;

@Controller
public class HomeController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private FineService fineService;


	// go to home
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		return "redirect:/home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	// go to home
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String fparkingLogoClick(Map<String, Object> model) {
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Driver> listDriver;
		List<Parking> listParking;
		List<Feedback> listFeedback;
		List<Booking> listBooking;
		List<Fine> listFine;

		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
			listFeedback = feedbackService.getAll();
			listBooking = bookingService.getAll();
			listFine = fineService.getAll();

			if (listDriver != null && listDriver.size() > 0) {
				model.put("totalAccountDriver", listDriver.size());
			} else {
				model.put("totalAccountDriver", 0);
			}

			if (listDriver != null && listDriver.size() > 0) {
				model.put("totalAccountParking", listParking.size());
			} else {
				model.put("totalAccountParking", 0);
			}

			// get list parking will be close
			ArrayList<Map<String, Object>> arrayListParking = new ArrayList<>();
			ArrayList<Map<String, Object>> arrayListParkingPending = new ArrayList<>();
			for (Parking parking : listParking) {
				HashMap<String, Object> m = new HashMap<>();
				HashMap<String, Object> m1 = new HashMap<>();
				if (parking.getStatus() == 2 && parking.getDeposits() <= 100000) {
					m.put("id", parking.getId());
					m.put("addressParking", parking.getAddress());
					m.put("nameOwner", parking.getOwner().getName());
					m.put("phoneOwner", parking.getOwner().getPhone());

					if (parking.getDeposits() % 1 == 0) {
						m.put("deposits", currencyVN.format((int) parking.getDeposits()));
					} else {
						m.put("deposits", currencyVN.format(parking.getDeposits()));
					}
					arrayListParking.add(m);
				}

				if (parking.getStatus() == 3) {
					m1.put("id", parking.getId());
					m1.put("addressParking", parking.getAddress());
					m1.put("nameOwner", parking.getOwner().getName());
					m1.put("phoneOwner", parking.getOwner().getPhone());

					if (parking.getDeposits() % 1 == 0) {
						m1.put("deposits", currencyVN.format((int) parking.getDeposits()));
					} else {
						m1.put("deposits", currencyVN.format(parking.getDeposits()));
					}
					arrayListParkingPending.add(m1);
				}
			}
			model.put("arrayListParking", arrayListParking);
			model.put("arrayListParkingPending", arrayListParkingPending);

			// get all new feedback
			ArrayList<Map<String, Object>> arrayListFeedback = new ArrayList<>();
			for (Feedback feedback : listFeedback) {
				HashMap<String, Object> m = new HashMap<>();
				if (feedback.getStatus() == 0) {
					m.put("id", feedback.getId());
					m.put("dateFeedBack", sdf.format(feedback.getDate()));
					String fb = feedback.getContent();

					if (fb.length() > 30) {
						m.put("content", fb.substring(0, 30) + "...");
					} else {
						m.put("content", fb);
					}

					m.put("nameFeedBack", feedback.getName());

					arrayListFeedback.add(m);
				}
			}
			model.put("arrayListFeedback", arrayListFeedback);

			// get all transaction in a day and reveune
			int totalTrasaction = 0;
			double totalReveune = 0;
			double revenueByFine = 0;
			double revenueByCommistion = 0;
			for (Booking booking : listBooking) {
				if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
						&& booking.getStatus() == 4) {
					totalTrasaction += 1;
					revenueByCommistion += booking.getAmount() * booking.getComission();
				}
			}

			for (Fine fine : listFine) {
				if (fine.getDate() != null && timeStamp.equals(sdf.format(fine.getDate())) && fine.getStatus() == 1) {
					totalTrasaction += 1;
					revenueByCommistion += fine.getPrice();
				}
			}
			model.put("totalTrasaction", totalTrasaction);

			totalReveune = revenueByCommistion + revenueByFine;
			if (totalReveune % 1 == 0) {
				model.put("totalReveune", currencyVN.format(totalReveune));
			} else {
				model.put("totalReveune", currencyVN.format(totalReveune));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "404";
		}

		return "home";
	}

	// check login
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model, @ModelAttribute Admin admin) {
		try {
			Admin admin2 = adminService.checklogin(admin);
		System.out.println("username+ passs:"+admin2.getUsername()+"---"+admin2.getPassword());
			if(admin2 !=null) {
				return "redirect:/home";
			}
		} catch (Exception e) {
			model.put("messError", "Tên đăng nhập hoặc mật khẩu không đúng!");
			return "login";
		}
		return "redirect:/home";
	}

	// get feedback detail
	@RequestMapping(path = "/home/feedbackdetail/{id}", method = RequestMethod.GET)
	public String getFeedBackDetail(Map<String, Object> model, @PathVariable("id") Long id) {
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
			model.put("messError", "Đã có lỗi xảy ra. Vui lòng thử lại");
			return "error";
		}
		model.put("id", id);
		model.put("inforFeedBack", feedback.getName() + "_" + feedback.getPhone());
		model.put("content", feedback.getContent());
		model.put("dateFeedBack", sdf.format(feedback.getDate()));

		return "viewdetailfeedback";
	}

	// delete feedback
	@RequestMapping(path = "/home/feedback/delete/{id}", method = RequestMethod.GET)
	public String deleteFeedBack(Map<String, Object> model, @PathVariable("id") Long id) {
		try {
			Feedback feedbackupdate = new Feedback();

			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setStatus(2);
			feedbackService.update(feedbackupdate);
		} catch (Exception e) {
			return "404";
		}

		return "redirect:/home";
	}

	// go to add money
	@RequestMapping(path = "/home/addmoney/{id}", method = RequestMethod.GET)
	public String getFỏmMoneyToParking(Map<String, Object> model, @PathVariable("id") Long id) {
		Parking parking = new Parking();
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		model.put("address", parking.getAddress());

		return "addmoneytoparking";
	}

	// add money
	@RequestMapping(path = "/home/addmoney/{id}", method = RequestMethod.POST)
	public String addMoneyToParking(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("deposit") Double deposit) {
		Parking parking = new Parking();
		try {
			parking = parkingService.getById(id);
			parking.setStatus(1);
			parking.setDeposits(parking.getDeposits() + deposit);
			parkingService.update(parking);
		} catch (Exception e) {
			return "404";
		}
		model.put("messSuss", "Nạp tiền thành công");

		return "addmoneytoparking";
	}
}