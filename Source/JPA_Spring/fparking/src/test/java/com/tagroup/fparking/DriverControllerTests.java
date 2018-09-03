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
import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.security.AuthenticationFilter;
import com.tagroup.fparking.service.domain.Driver;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class DriverControllerTests {

	private MockMvc mockMvc;
	private Driver DriverForTest;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private DriverRepository driverRepository;
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
		System.out.println(res.getToken());
		// create driver for test
		DriverForTest = new Driver();
		String name = "Name test";
		String phone = "0968949066";
		String password = "Password test";
		int status = 1;
		DriverForTest.setName(name);
		DriverForTest.setPhone(phone);
		DriverForTest.setPassword(password);
		DriverForTest.setStatus(status);

		DriverForTest = driverRepository.save(DriverForTest);
	}

	//
	@Test
	public void whenGetAllDrivers_thenReturnJsonArray() throws Exception {
		mockMvc.perform(get("/api/drivers/").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenGetOneDriver_thenReturnJsonObject() throws Exception {
		mockMvc.perform(get("/api/drivers/" + DriverForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenGetInvalidDriverId_thenReturnBadRequest() throws Exception {
		mockMvc.perform(get("/api/drivers/1L").header("Authorization", "Bearer " + token)).andExpect(status().is(400));

		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenCreateDriver_thenReturnDriver() throws Exception {
		Driver responseDriver = null;
		try {
			Driver driver = new Driver();
			driver.setName("Name test2");
			driver.setPhone("0968949065");
			driver.setPassword("Password test2");

			String json = gson.toJson(driver);
			String responseJson = mockMvc
					.perform(post("/api/drivers").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseDriver = gson.fromJson(responseJson, Driver.class);

			assertEquals(1, responseDriver.getStatus());
			assertNotNull(responseDriver.getId());
		} finally {
			if (responseDriver != null && responseDriver.getId() != null) {
				driverRepository.deleteById(responseDriver.getId());

			}
			driverRepository.deleteById(DriverForTest.getId());
		}
	}

	@Test
	public void whenCreateDriverWithoutName_thenReturnError() throws Exception {
		Driver driver = new Driver();
		driver.setPhone("0968949065");
		driver.setPassword("Password test2");
		String json = gson.toJson(driver);
		mockMvc.perform(post("/api/drivers").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenCreateDriverWithoutPassword_thenReturnError() throws Exception {
		Driver driver = new Driver();
		driver.setName("Name test2");
		driver.setPhone("0968949065");
		
		String json = gson.toJson(driver);
		mockMvc.perform(post("/api/drivers").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenCreateDriverWithoutPhone_thenReturnError() throws Exception {
		Driver driver = new Driver();
		driver.setName("Name test2");
		driver.setPassword("Password test2");
		
		String json = gson.toJson(driver);
		mockMvc.perform(post("/api/drivers").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenCreateDriverPhoneIsExist_thenReturnError() throws Exception {
		Driver driver = new Driver();
		driver.setName("Name test2");
		driver.setPhone(DriverForTest.getPhone());
		driver.setPassword("Password test2");
		
		String json = gson.toJson(driver);
		mockMvc.perform(post("/api/drivers").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverRepository.deleteById(DriverForTest.getId());
	}

	@Test
	public void whenUpdateDriver_thenReturnDriver() throws Exception {
		Driver responseDriver = null;
		try {

			Driver DriverRequest = DriverForTest;
			DriverRequest.setStatus(0);
			String json = gson.toJson(DriverRequest);
			String responseJson = mockMvc
					.perform(put("/api/drivers").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseDriver = gson.fromJson(responseJson, Driver.class);
			assertEquals(DriverRequest.getStatus(), responseDriver.getStatus());
			assertNotNull(responseDriver.getId());
		} finally {
			if (responseDriver != null && responseDriver.getId() != null) {
				driverRepository.deleteById(responseDriver.getId());

			}
		}
	}

	@Test
	public void whenUpdateDriverWrongDriverid_thenReturnError() throws Exception {
		// Booking responseBooking = null;
		try {

			Driver DriverRequest = new Driver();
			DriverRequest.setId((long) 123123123);
			DriverRequest.setStatus(1);
			String json = gson.toJson(DriverRequest);
			mockMvc.perform(put("/api/drivers").header("Authorization", "Bearer " + token).content(json)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404));
		} finally {

			driverRepository.deleteById(DriverForTest.getId());
		}
	}

	@Test
	public void whenUpdateDriverWithoutDriverId_thenReturnError() throws Exception {
		// Booking responseBooking = null;
		try {

			Driver DriverRequest = new Driver();
			DriverRequest.setStatus(1);
			String json = gson.toJson(DriverRequest);
			mockMvc.perform(put("/api/drivers").header("Authorization", "Bearer " + token).content(json)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404));
		} finally {

			driverRepository.deleteById(DriverForTest.getId());
		}
	}

	@Test
	public void whenDeleteDriver_thenReturnOkhttprequest() throws Exception {
		mockMvc.perform(delete("/api/drivers/" + DriverForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().is(200));
	}
	
	@Test
	public void whenDeleteDriverWrongDriverId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/drivers/" + 123123123).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		driverRepository.deleteById(DriverForTest.getId());
	}
	@Test
	public void whenDeleteDriverInvalidDriverId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/drivers/" + 1L).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		driverRepository.deleteById(DriverForTest.getId());
	}
}
