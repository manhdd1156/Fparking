package com.tagroup.fparking.controller.webadmin;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.security.TokenProvider;
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
	@Autowired
	private TokenProvider tokenProvider;

	// String timeStamp = new
	// SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
	Locale locale = new Locale("vi", "VN");

	String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

	// go to home
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		return "redirect:/home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	// go to home
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String homePage(Map<String, Object> model) {
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

			if (listDriver != null && listParking.size() > 0) {
				model.put("totalAccountParking", listParking.size());
			} else {
				model.put("totalAccountParking", 0);
			}

			// get list parking will be close and peding
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

				// Pending
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
				if (booking.getTimeout() != null && booking.getStatus() == 3
						&& timeStamp.equals(sdf.format(booking.getTimeout()))) {
					totalTrasaction += 1;
					revenueByCommistion += booking.getAmount() * booking.getComission();
				}
			}

			for (Fine fine : listFine) {
				if (fine.getDate() != null && fine.getStatus() == 1 && timeStamp.equals(sdf.format(fine.getDate()))) {
					revenueByCommistion += fine.getPrice();
				}
			}
			model.put("totalTrasaction", totalTrasaction);

			totalReveune = revenueByCommistion + revenueByFine;
			model.put("totalReveune", currencyVN.format(totalReveune));
		} catch (Exception e) {
			return "404";
		}

		return "home";
	}

	// check login
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model, @ModelAttribute Admin admin, HttpServletRequest request) {
		try {
			Admin admin2 = adminService.checklogin(admin);
			Token token = new Token();
			token.setId(admin2.getId());
			token.setType("ADMIN");
			String jwt = tokenProvider.generateToken(token);
			request.getSession().setAttribute("Authorization", "Bearer " + jwt);
			if (admin2 != null) {
				return "redirect:/home";
			}
		} catch (Exception e) {
			model.put("username", admin.getUsername());
			model.put("messError", "Tên đăng nhập hoặc mật khẩu không đúng!");
			return "login";
		}
		return "redirect:/home";
	}

	// get feedback detail
	@PreAuthorize("hasAnyAuthority('ADMIN')")
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
			return "404";
		}
		model.put("id", id);
		model.put("inforFeedBack", feedback.getName() + "_" + feedback.getPhone());
		model.put("content", feedback.getContent());
		model.put("dateFeedBack", sdf.format(feedback.getDate()));

		return "viewdetailfeedback";
	}

	// delete feedback
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/feedback/delete/{id}", method = RequestMethod.GET)
	public String deleteFeedBack(Map<String, Object> model, @PathVariable("id") Long id) {
		Feedback feedbackupdate = new Feedback();
		try {
			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setStatus(2);
			feedbackService.update(feedbackupdate);
		} catch (Exception e) {
			return "404";
		}

		return "redirect:/home";
	}

	// resolve feedback
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/feedbackdetail/{id}", method = RequestMethod.POST)
	public String getFeedBackDetail(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("resolve") String resolve) {
		Feedback feedbackupdate = new Feedback();
		sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		try {
			feedbackupdate = feedbackService.getById(id);
			feedbackupdate.setType(1);
			feedbackupdate.setResolve(resolve);
			feedbackService.update(feedbackupdate);
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

	// go to add money
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/addmoney/{id}", method = RequestMethod.GET)
	public String getFormAcceptNewParking(Map<String, Object> model, @PathVariable("id") Long id) {
		Parking parking = new Parking();
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		model.put("address", parking.getAddress());

		return "acceptnewparking";
	}

	// accept new parking
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/addmoney/{id}", method = RequestMethod.POST)
	public String acceptNewParking(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("deposit") Double deposit) {
		Parking parking = new Parking();
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		try {
			parking.setStatus(1);
			parking.setDeposits(parking.getDeposits() + deposit);
			parking = parkingService.update(parking);
			model.put("deposit", currencyVN.format(parking.getDeposits()));
			model.put("address", parking.getAddress());
		} catch (Exception e) {
			model.put("deposit", deposit);
			model.put("address", parking.getAddress());
			model.put("messError", "Nạp tiền không thành công!");
			return "addmoneytoparking";
		}
		model.put("messSuss", "Nạp thành công " + currencyVN.format(deposit));
		return "acceptnewparking";
	}

	// get all revenuve by commission
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/revenue/commission", method = RequestMethod.GET)
	public String getAllRevenueByCommission(Map<String, Object> model) throws Exception {
		int check = 0;
		List<Booking> listBooking;
		double revenueCommission = 0;
		ArrayList<Map<String, Object>> arrayListBooking = new ArrayList<>();
		try {
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			return "404";
		}
		for (Booking booking : listBooking) {
			HashMap<String, Object> m = new HashMap<>();
			if (booking.getStatus() == 3 && booking.getTimeout() != null
					&& timeStamp.equals(sdf.format(booking.getTimeout()))) {
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
		return "managementrevenuebycommission";
	}

	// get driver by id
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@RequestMapping(path = "/home/statistic", method = RequestMethod.GET)
	public ResponseEntity<?> getStatistics() throws Exception {
		HashMap<String, Object> model = new HashMap<>();
		List<Driver> listDriver;
		List<Parking> listParking;
		List<Booking> listBooking;
		List<Fine> listFine;

		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
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

			// get all transaction in a day and reveune
			int totalTrasaction = 0;
			double totalReveune = 0;
			double revenueByFine = 0;
			double revenueByCommistion = 0;
			for (Booking booking : listBooking) {
				if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
						&& booking.getStatus() == 3) {
					totalTrasaction += 1;
					revenueByCommistion += booking.getAmount() * booking.getComission();
				}
			}
			for (Fine fine : listFine) {
				if (fine.getDate() != null && timeStamp.equals(sdf.format(fine.getDate())) && fine.getStatus() == 1) {
					revenueByCommistion += fine.getPrice();
				}
			}
			model.put("totalTrasaction", totalTrasaction);
			totalReveune = revenueByCommistion + revenueByFine;
			model.put("totalReveune", currencyVN.format(totalReveune));
			return new ResponseEntity<>(model, HttpStatus.OK);

		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", "/404");
			return new ResponseEntity<String>(headers, HttpStatus.FOUND);
		}
	}

}