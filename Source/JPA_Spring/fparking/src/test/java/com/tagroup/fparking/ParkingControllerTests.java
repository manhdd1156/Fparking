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
import com.tagroup.fparking.dto.ParkingDTO;
import com.tagroup.fparking.repository.CityRepository;
import com.tagroup.fparking.repository.OwnerRepository;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.security.AuthenticationFilter;
import com.tagroup.fparking.service.domain.City;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class ParkingControllerTests {

	private MockMvc mockMvc;
	private Parking ParkingForTest;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private ParkingRepository parkingRepository;
	@Autowired
	private CityRepository cityRepository;
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

		// create parking for test
		ParkingForTest = new Parking();
		City city = cityRepository.findAll().get(0);
		Owner owner = ownerRepository.findAll().get(0);
		String address = "Address test";
		String latitude = "150.11152";
		String longitude = "20.200052";
		int totalspace = 30;
		String timeoc = "05:00-24:00h";
		int status = 1;
		ParkingForTest.setAddress(address);
		ParkingForTest.setOwner(owner);
		ParkingForTest.setCity(city);
		ParkingForTest.setLatitude(latitude);
		ParkingForTest.setLongitude(longitude);
		ParkingForTest.setTotalspace(totalspace);
		ParkingForTest.setTimeoc(timeoc);
		ParkingForTest.setStatus(status);
		ParkingForTest = parkingRepository.save(ParkingForTest);
	}

	//
	@Test
	public void whenGetAllParkings_thenReturnJsonArray() throws Exception {
		mockMvc.perform(get("/api/parkings/").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenGetOneParking_thenReturnJsonObject() throws Exception {
		mockMvc.perform(get("/api/parkings/" + ParkingForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenGetWrongParkingId_thenReturnNotFound() throws Exception {
		mockMvc.perform(get("/api/parkings/123123123").header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));

		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenGetInvalidParkingId_thenReturnBadRequest() throws Exception {
		mockMvc.perform(get("/api/parkings/1L").header("Authorization", "Bearer " + token)).andExpect(status().is(400));

		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParking_thenReturnParking() throws Exception {
		Parking responseParking = null;
		try {
			ParkingDTO parkingDTO = new ParkingDTO();
			parkingDTO.setAddress("Address test2");
			parkingDTO.setLongitude("150.11152");
			parkingDTO.setLatitude("20.200052");
			parkingDTO.setTimeoc("05:00-24:00h");
			parkingDTO.setTotalspace(30);
			parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
			parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());

			String json = gson.toJson(parkingDTO);
			String responseJson = mockMvc
					.perform(post("/api/parkings").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseParking = gson.fromJson(responseJson, Parking.class);

			assertEquals(3, responseParking.getStatus());
			assertNotNull(responseParking.getId());
		} finally {
			if (responseParking != null && responseParking.getId() != null) {
				parkingRepository.deleteById(responseParking.getId());

			}
			parkingRepository.deleteById(ParkingForTest.getId());
		}
	}

	@Test
	public void whenCreateParkingWithoutAddress_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
		parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingWithoutLongitudeAndLatitude_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
		parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingWithoutTimeoc_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
		parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingWithoutCityId_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingInvalidCityId_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id((long) 123456);
		parkingDTO.setOwner_id(ownerRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingWithoutOwnerId_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenCreateParkingInvalidOwnerId_thenReturnError() throws Exception {
		ParkingDTO parkingDTO = new ParkingDTO();
		parkingDTO.setAddress("Address test2");
		parkingDTO.setLongitude("150.11152");
		parkingDTO.setLatitude("20.200052");
		parkingDTO.setTimeoc("05:00-24:00h");
		parkingDTO.setTotalspace(30);
		parkingDTO.setCity_id(cityRepository.findAll().get(0).getId());
		parkingDTO.setOwner_id((long) 123456);
		String json = gson.toJson(parkingDTO);
		mockMvc.perform(post("/api/parkings").header("Authorization", "Bearer " + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		parkingRepository.deleteById(ParkingForTest.getId());
	}

	@Test
	public void whenUpdateParking_thenReturnParking() throws Exception {
		Parking responseParking = null;
		try {

			Parking parkingRequest = ParkingForTest;
			parkingRequest.setStatus(1);
			String json = gson.toJson(parkingRequest);
			String responseJson = mockMvc
					.perform(post("/api/parkings/update").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseParking = gson.fromJson(responseJson, Parking.class);
			assertEquals(parkingRequest.getStatus(), responseParking.getStatus());
			assertNotNull(responseParking.getId());
		} finally {
			if (responseParking != null && responseParking.getId() != null) {
				parkingRepository.deleteById(responseParking.getId());

			}
		}
	}

	@Test
	public void whenUpdateParkingWrongParkingid_thenReturnError() throws Exception {
		try {

			Parking parkingRequest = new Parking();
			parkingRequest.setId((long) 123123123);
			parkingRequest.setStatus(1);
			String json = gson.toJson(parkingRequest);
			mockMvc.perform(post("/api/parkings/update/").header("Authorization", "Bearer " + token).content(json)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404));
		} finally {

			parkingRepository.deleteById(ParkingForTest.getId());
		}
	}

	@Test
	public void whenUpdateParkingWithoutParkingId_thenReturnError() throws Exception {
		try {

			Parking parkingRequest = new Parking();
			parkingRequest.setStatus(1);
			String json = gson.toJson(parkingRequest);
			mockMvc.perform(post("/api/parkings/update/").header("Authorization", "Bearer " + token).content(json)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404));
		} finally {

			parkingRepository.deleteById(ParkingForTest.getId());
		}
	}

	@Test
	public void whenDeleteParking_thenReturnOkhttprequest() throws Exception {
		mockMvc.perform(delete("/api/parkings/" + ParkingForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().is(200));
	}
	@Test
	public void whenDeleteDriverWrongParkingId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/parkings/" + 123123123).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		parkingRepository.deleteById(ParkingForTest.getId());
	}
	
	@Test
	public void whenDeleteDriverInvalidParkingId_thenReturnError() throws Exception {
		mockMvc.perform(delete("/api/parkings/" + 1L).header("Authorization", "Bearer " + token))
				.andExpect(status().is(404));
		parkingRepository.deleteById(ParkingForTest.getId());
	}
}
