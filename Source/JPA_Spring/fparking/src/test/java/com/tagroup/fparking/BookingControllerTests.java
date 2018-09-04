package com.tagroup.fparking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tagroup.fparking.dto.BookingDTO;
import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.security.AuthenticationFilter;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Parking;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class BookingControllerTests {

	private MockMvc mockMvc;
	private Booking BookingForTest;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private AuthenticationFilter authenticationFilter;

	private Gson gson;

	private String token;

	@Before
	public void init() throws Exception {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(authenticationFilter).build();

		// create user admin to get token
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setUsername("admin");
		dto.setPassword("fcea920f7412b5da7be0cf42b8c93759");
		dto.setType("ADMIN");
		String json = gson.toJson(dto);
		String response = mockMvc.perform(post("/api/login").content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();
		LoginResponseDTO res = gson.fromJson(response, LoginResponseDTO.class);
		token = res.getToken();

		// create booking for test
		BookingForTest = new Booking();
		DriverVehicle drivervehicle = driverVehicleRepository.findAll().get(0);

		Parking parking = parkingRepository.findAll().get(0);
		BookingForTest.setDrivervehicle(drivervehicle);
		BookingForTest.setParking(parking);
		BookingForTest = bookingRepository.save(BookingForTest);
	}

	//
	@Test
	public void whenGetAllBookings_thenReturnJsonArray() throws Exception {
		mockMvc.perform(get("/api/bookings/").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		bookingRepository.deleteById(BookingForTest.getId());
	}

	@Test
	public void whenGetOneBooking_thenReturnJsonObject() throws Exception {
		mockMvc.perform(get("/api/bookings/" + BookingForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		bookingRepository.deleteById(BookingForTest.getId());
	}

	@Test
	public void whenGetWrongBookingId_thenReturnNotFound() throws Exception {
		mockMvc.perform(get("/api/bookings/123123123").header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));

		bookingRepository.deleteById(BookingForTest.getId());
	}
	@Test
	public void whenGetBookingIdIsZero_thenReturnNotFound() throws Exception {
		mockMvc.perform(get("/api/bookings/0").header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));

		bookingRepository.deleteById(BookingForTest.getId());
	}
	@Test
	public void whenGetInvalidBookingId_thenReturnBadRequest() throws Exception {
		mockMvc.perform(get("/api/bookings/1L").header("Authorization", "Bearer " + token)).andExpect(status().is(400));

		bookingRepository.deleteById(BookingForTest.getId());
	}

	 @Test
	 public void whenCreateBooking_thenReturnBooking() throws Exception {
	 Booking responseBooking = null;
	 try {
	 BookingDTO bookingDTO = new BookingDTO();
	 bookingDTO.setDriverid(driverVehicleRepository.findAll().get(0).getDriver().getId());
	 bookingDTO.setVehicleid(driverVehicleRepository.findAll().get(0).getVehicle().getId());
	 bookingDTO.setParkingid(parkingRepository.findAll().get(0).getId());
	 bookingDTO.setStatus(5);
	
	 String json = gson.toJson(bookingDTO);
	 String responseJson = mockMvc
	 .perform(post("/api/bookings/create").header("Authorization", "Bearer " +
	 token).content(json)
	 .contentType(MediaType.APPLICATION_JSON))
	 .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();
	 responseBooking = gson.fromJson(responseJson, Booking.class);
	
	 assertEquals(bookingDTO.getDriverid(),
	 responseBooking.getDrivervehicle().getDriver().getId());
	 assertNotNull(responseBooking.getId());
	 } finally {
	 if (responseBooking != null && responseBooking.getId() != null) {
	 bookingRepository.deleteById(responseBooking.getId());
	
	 }
	 bookingRepository.deleteById(BookingForTest.getId());
	 }
	 }
	
	 @Test
	 public void whenCreateBookingWithoutParkingId_thenReturnError() throws
	 Exception {
	 BookingDTO bookingDTO = new BookingDTO();
	 bookingDTO.setDriverid(driverVehicleRepository.findAll().get(0).getDriver().getId());
	 bookingDTO.setVehicleid(driverVehicleRepository.findAll().get(0).getVehicle().getId());
	 bookingDTO.setStatus(5);
	 String json = gson.toJson(bookingDTO);
	 mockMvc.perform(post("/api/bookings/create").header("Authorization", "Bearer" + token).content(json)
	 .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
	 bookingRepository.deleteById(BookingForTest.getId());
	 }
	
	 @Test
	 public void whenCreateBookingWithoutDriveridId_thenReturnError() throws
	 Exception {
	 BookingDTO bookingDTO = new BookingDTO();
	 bookingDTO.setVehicleid(driverVehicleRepository.findAll().get(0).getVehicle().getId());
	 bookingDTO.setParkingid(parkingRepository.findAll().get(0).getId());
	 bookingDTO.setStatus(5);
	 String json = gson.toJson(bookingDTO);
	 mockMvc.perform(post("/api/bookings/create").header("Authorization", "Bearer" + token).content(json)
	 .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
	 bookingRepository.deleteById(BookingForTest.getId());
	 }
	
	 @Test
	 public void whenCreateBookingWithoutVehicleidId_thenReturnError() throws
	 Exception {
	 BookingDTO bookingDTO = new BookingDTO();
	 bookingDTO.setDriverid(driverVehicleRepository.findAll().get(0).getDriver().getId());
	 bookingDTO.setParkingid(parkingRepository.findAll().get(0).getId());
	 bookingDTO.setStatus(5);
	 String json = gson.toJson(bookingDTO);
	 mockMvc.perform(post("/api/bookings/create").header("Authorization", "Bearer"
	  + token).content(json)
	 .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
	 bookingRepository.deleteById(BookingForTest.getId());
	 }

	@Test
	public void whenCreateBookingWithoutVehicleidIdAndDriveridAndParkingId_thenReturnError() throws Exception {
		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setStatus(5);
		String json = gson.toJson(bookingDTO);
		mockMvc.perform(post("/api/bookings/create").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		bookingRepository.deleteById(BookingForTest.getId());
	}

	@Test
	public void whenUpdateBooking_thenReturnBooking() throws Exception {
		Booking responseBooking = null;
		try {

			Booking bookingRequest = BookingForTest;
			bookingRequest.setStatus(3);
			String json = gson.toJson(bookingRequest);
			String responseJson = mockMvc
					.perform(put("/api/bookings/update").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseBooking = gson.fromJson(responseJson, Booking.class);
//			System.out.println(responseJson);
//			System.out.println(BookingForTest);
//			System.out.println(responseBooking);
			assertEquals(bookingRequest.getStatus(), responseBooking.getStatus());
			assertNotNull(responseBooking.getId());
		} finally {
			if (responseBooking != null && responseBooking.getId() != null) {
				 bookingRepository.deleteById(responseBooking.getId());

			}
			// bookingRepository.deleteById(BookingForTest.getId());
		}
	}

	@Test
	public void whenUpdateBookingWrongBookingid_thenReturnError() throws Exception {
//		Booking responseBooking = null;
		try {

			Booking bookingRequest = new Booking();
			bookingRequest.setId((long)123123123);
			bookingRequest.setStatus(3);
			String json = gson.toJson(bookingRequest);
			mockMvc.perform(put("/api/bookings/update/").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(404));
		} finally {
			
			bookingRepository.deleteById(BookingForTest.getId());
		}
	}
	
	@Test
	public void whenUpdateBookingWithoutBookingId_thenReturnError() throws Exception {
//		Booking responseBooking = null;
		try {

			Booking bookingRequest = new Booking();
//			bookingRequest.setId((long)14323);
			bookingRequest.setStatus(3);
			String json = gson.toJson(bookingRequest);
			mockMvc.perform(put("/api/bookings/update/").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(404));
		} finally {
			
			bookingRepository.deleteById(BookingForTest.getId());
		}
	}
	@Test
	public void whenDeleteBooking_thenReturnOkhttprequest() throws Exception {
		mockMvc.perform(delete("/api/bookings/" + BookingForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().is(200));
	}
	@Test
	public void whenDeleteDriverWrongBookingId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/bookings/" + 123123123).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		bookingRepository.deleteById(BookingForTest.getId());
	}
	
	@Test
	public void whenDeleteDriverInvalidParkingId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/bookings/" + 1L).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		bookingRepository.deleteById(BookingForTest.getId());
	}
}
