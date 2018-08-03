package com.tagroup.fparking.controller.webadmin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.service.AdminService;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Admin;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Feedback;
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

	// go to home
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Driver> listDriver;
		List<Parking> listParking;
		List<Feedback> listFeedback;
		List<Booking> listBooking;
		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
			listFeedback = feedbackService.getAll();
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "404";
		}

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
		for (Parking parking : listParking) {
			HashMap<String, Object> m = new HashMap<>();
			if (parking.getDeposits() <= 100000) {
				m.put("addressParking", parking.getAddress());
				m.put("nameOwner", parking.getOwner().getName());
				m.put("phoneOwner", parking.getOwner().getPhone());
				
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", (int) parking.getDeposits());
				} else {
					m.put("deposits", parking.getDeposits());
				}
				arrayListParking.add(m);
			}
		}
		model.put("arrayListParking", arrayListParking);

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

		// get all transaction in a day
		int totalTrasaction = 0;
		for (Booking booking : listBooking) {
			if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
					&& booking.getStatus() == 4) {
				totalTrasaction += 1;
			}
		}
		model.put("totalTrasaction", totalTrasaction);
		return "home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	// go to home
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String fparkingLogoClick(Map<String, Object> model) {
		List<Driver> listDriver;
		List<Parking> listParking;
		List<Feedback> listFeedback;
		List<Booking> listBooking;
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
			listFeedback = feedbackService.getAll();
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "404";
		}

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
		for (Parking parking : listParking) {
			HashMap<String, Object> m = new HashMap<>();
			if (parking.getDeposits() <= 100000) {
				m.put("addressParking", parking.getAddress());
				m.put("nameOwner", parking.getOwner().getName());
				m.put("phoneOwner", parking.getOwner().getPhone());
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", (int) parking.getDeposits());
				} else {
					m.put("deposits", parking.getDeposits());
				}
				arrayListParking.add(m);
			}
		}
		model.put("arrayListParking", arrayListParking);

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

		// get all transaction in a day
		int totalTrasaction = 0;
		for (Booking booking : listBooking) {
			if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
					&& booking.getStatus() == 4) {
				totalTrasaction += 1;
			}
		}
		model.put("totalTrasaction", totalTrasaction);
		return "home";
	}

	// check login
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model, @ModelAttribute Admin admin) {
		List<Driver> listDriver;
		List<Parking> listParking;
		List<Feedback> listFeedback;
		List<Booking> listBooking;
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
			listFeedback = feedbackService.getAll();
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			return "404";
		}
		try {
			Admin admin2 = adminService.checklogin(admin);
		} catch (Exception e) {
			model.put("messError", "Tên đăng nhập hoặc mật khẩu không đúng!");
			return "login";
		}

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
		for (Parking parking : listParking) {
			HashMap<String, Object> m = new HashMap<>();
			if (parking.getDeposits() <= 100000) {
				m.put("addressParking", parking.getAddress());
				m.put("nameOwner", parking.getOwner().getName());
				m.put("phoneOwner", parking.getOwner().getPhone());
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", (int) parking.getDeposits());
				} else {
					m.put("deposits", parking.getDeposits());
				}
				arrayListParking.add(m);
			}
		}
		model.put("arrayListParking", arrayListParking);

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

		// get all transaction in a day
		int totalTrasaction = 0;
		for (Booking booking : listBooking) {
			if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
					&& booking.getStatus() == 4) {
				totalTrasaction += 1;
			}
		}
		model.put("totalTrasaction", totalTrasaction);
		return "home";
	}

	// get feedback detail
	@RequestMapping(path = "/home/feedbackdetail/{id}", method = RequestMethod.GET)
	public String getFeedBackDetail(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		Feedback feedback = new Feedback();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			feedback = feedbackService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		model.put("id", id);
		model.put("inforFeedBack", feedback.getName() + "_" + feedback.getPhone());
		model.put("content", feedback.getContent());
		model.put("dateFeedBack",sdf.format(feedback.getDate()));

		return "viewdetailfeedback";
	}

	// delete feedback
	@RequestMapping(path = "/home/feedback/delete/{id}", method = RequestMethod.GET)
	public String deleteFeedBack(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Feedback feedbackupdate = new Feedback();
		try {
			feedbackupdate = feedbackService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		feedbackupdate.setStatus(2);

		try {
			feedbackService.update(feedbackupdate);
		} catch (Exception e) {
			return "404";
		}

		List<Driver> listDriver;
		List<Parking> listParking;
		List<Feedback> listFeedback;
		List<Booking> listBooking;
		try {
			listDriver = driverService.getAll();
			listParking = parkingService.getAll();
			listFeedback = feedbackService.getAll();
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			return "404";
		}
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
		for (Parking parking : listParking) {
			HashMap<String, Object> m = new HashMap<>();
			if (parking.getDeposits() <= 100000) {
				m.put("addressParking", parking.getAddress());
				m.put("nameOwner", parking.getOwner().getName());
				m.put("phoneOwner", parking.getOwner().getPhone());
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", (int) parking.getDeposits());
				} else {
					m.put("deposits", parking.getDeposits());
				}
				arrayListParking.add(m);
			}
		}
		model.put("arrayListParking", arrayListParking);

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

		// get all transaction in a day
		int totalTrasaction = 0;
		for (Booking booking : listBooking) {
			if (booking.getTimeout() != null && timeStamp.equals(sdf.format(booking.getTimeout()))
					&& booking.getStatus() == 4) {
				totalTrasaction += 1;
			}
		}
		model.put("totalTrasaction", totalTrasaction);
		return "home";
	}
}