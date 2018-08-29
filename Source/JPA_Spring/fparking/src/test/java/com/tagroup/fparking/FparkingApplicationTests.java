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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.dto.UserLoginRequestDTO;
import com.tagroup.fparking.repository.BookingRepository;
import com.tagroup.fparking.security.AuthenticationFilter;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Parking;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class FparkingApplicationTests {
	
	private MockMvc mockMvc;
	private Booking BookingForTest;
    @Autowired
    private WebApplicationContext webApplicationContext;	
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
   	private AuthenticationFilter authenticationFilter;
    
    private Gson gson;
    
    private String token;
    
    @Before
	public void init() throws Exception {
    	gson = new GsonBuilder()
				   .setDateFormat("yyyy-MM-dd").create();
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.addFilters(authenticationFilter)
					.build();
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setUsername("admin");
		dto.setPassword("fcea920f7412b5da7be0cf42b8c93759");
		dto.setType("ADMIN");
		String json = gson.toJson(dto);
		String response = mockMvc.perform(post("/api/login")
				.content(json)
        		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().is(200))
    		.andReturn().getResponse().getContentAsString();
		LoginResponseDTO res = gson.fromJson(response, LoginResponseDTO.class);
		token = res.getToken();
		BookingForTest = new Booking();
		DriverVehicle drivervehicle = new DriverVehicle();
		drivervehicle.setId((long)1);
		Parking parking = new Parking();
		parking.setId((long)1);
		BookingForTest.setDrivervehicle(drivervehicle);
		BookingForTest.setParking(parking);
		BookingForTest = bookingRepository.save(BookingForTest);
    }
    
	@Test
	public void whenGetAllBookings_thenReturnJsonArray()
		      throws Exception {
			mockMvc.perform(get("/api/bookings/").header("Authorization", "Bearer " + token))
			.andExpect(status().isOk());
	}
	
	@Test
    public void whenGetOneBooking_thenReturnJsonObject()
      throws Exception {
        mockMvc.perform(get("/api/bookings/"+BookingForTest.getId()).header("Authorization", "Bearer " + token))
        	.andExpect(status().isOk());
    }
	@Test
    public void whenGetWrongBookingId_thenReturnNotFound()
      throws Exception {
        mockMvc.perform(get("/api/bookings/123123123").header("Authorization", "Bearer " + token))
        	.andExpect(status().is(404));
    }
	@Test
    public void whenGetInvalidBookingId_thenReturnBadRequest()
      throws Exception {
        mockMvc.perform(get("/api/bookings/1L").header("Authorization", "Bearer " + token))
        .andExpect(status().is(400));
    }

}
