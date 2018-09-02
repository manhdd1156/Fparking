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

import java.util.List;

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
import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.repository.VehicletypeRepository;
import com.tagroup.fparking.security.AuthenticationFilter;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;
import com.tagroup.fparking.service.domain.Vehicletype;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class VehicleControllerTests {

	private MockMvc mockMvc;
	private Vehicle VehicleForTest;
	private DriverVehicle DriverVehicleForTest;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private VehicletypeRepository vehicletypeRepository;
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
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
		// create Vehicle for test
		VehicleForTest = new Vehicle();
		String licenseplate = "30Z1-12345";
		Vehicletype vehicletype = vehicletypeRepository.findAll().get(0);
		int status = 1;
		VehicleForTest.setLicenseplate(licenseplate);
		VehicleForTest.setVehicletype(vehicletype);
		VehicleForTest.setStatus(status);
		VehicleForTest = vehicleRepository.save(VehicleForTest);
		// create drivervehicle for test
		DriverVehicleForTest = new DriverVehicle();
		Driver driver = driverRepository.findAll().get(0);
		DriverVehicleForTest.setDriver(driver);
		DriverVehicleForTest.setVehicle(VehicleForTest);
		DriverVehicleForTest.setStatus(status);
		DriverVehicleForTest = driverVehicleRepository.save(DriverVehicleForTest);
	}

	@Test
	public void whenGetAllvehicles_thenReturnJsonArray() throws Exception {
		mockMvc.perform(get("/api/vehicles/").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}
	@Test
	public void whenGetAllvehicleTypes_thenReturnJsonArray() throws Exception {
		mockMvc.perform(get("/api/vehicles/types").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}
	@Test
	public void whenGetOneVehicle_thenReturnJsonObject() throws Exception {
		mockMvc.perform(get("/api/vehicles/" + VehicleForTest.getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}

	@Test
	public void whenGetInvalidVehicleId_thenReturnBadRequest() throws Exception {
		mockMvc.perform(get("/api/vehicles/1L").header("Authorization", "Bearer " + token)).andExpect(status().is(400));

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}

	@Test
	public void whenGetOneDriverVehicle_thenReturnJsonObject() throws Exception {
		mockMvc.perform(get("/api/vehicles/drivers/" + DriverVehicleForTest.getDriver().getId()).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}

	@Test
	public void whenGetOneDriverVehicleInvalidDriverId_thenReturnBadRequest() throws Exception {
		mockMvc.perform(get("/api/vehicles/drivers/1L").header("Authorization", "Bearer " + token)).andExpect(status().is(400));

		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}
	
	@Test
	public void whenCreateVehicle_thenReturnDriverVehicle() throws Exception {
		DriverVehicle responseDriverVehicle = null;
		try {
			DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
			System.out.println("????????? driverid = " + DriverVehicleForTest.getDriver().getId());
			driverVehicleDTO.setDriverid(DriverVehicleForTest.getDriver().getId());
			driverVehicleDTO.setLicenseplate("30Z2-12345");
			driverVehicleDTO.setType("4 chỗ");
			
			String json = gson.toJson(driverVehicleDTO);
			String responseJson = mockMvc
					.perform(post("/api/vehicles").header("Authorization", "Bearer " + token).content(json)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseDriverVehicle = gson.fromJson(responseJson, DriverVehicle.class);

			assertEquals(1, responseDriverVehicle.getStatus());
			assertNotNull(responseDriverVehicle.getId());
		} finally {
			if (responseDriverVehicle != null && responseDriverVehicle.getId() != null) {
				driverVehicleRepository.deleteById(responseDriverVehicle.getId());
				vehicleRepository.deleteById(responseDriverVehicle.getVehicle().getId());

			}
			driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
			vehicleRepository.deleteById(VehicleForTest.getId());
		}
	}

	@Test
	public void whenCreateVehicleWithoutDriverId_thenReturnError() throws Exception {
		DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
		driverVehicleDTO.setLicenseplate("30Z2-12345");
		driverVehicleDTO.setType("4 chỗ");
		
		String json = gson.toJson(driverVehicleDTO);
		mockMvc.perform(post("/api/vehicles").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}

	@Test
	public void whenCreateVehicleWithoutLicenseplate_thenReturnError() throws Exception {
		DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
		driverVehicleDTO.setDriverid(DriverVehicleForTest.getDriver().getId());
		driverVehicleDTO.setType("4 chỗ");
		
		String json = gson.toJson(driverVehicleDTO);
		mockMvc.perform(post("/api/vehicles").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}

	@Test
	public void whenCreateVehicleWithoutType_thenReturnError() throws Exception {
		DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
		driverVehicleDTO.setDriverid(DriverVehicleForTest.getDriver().getId());
		driverVehicleDTO.setLicenseplate("30Z2-12345");
		
		String json = gson.toJson(driverVehicleDTO);
		mockMvc.perform(post("/api/vehicles").header("Authorization", "Bearer" + token).content(json)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}


	@Test
	public void whenUpdateDriverVehicle_thenReturnVehicle() throws Exception {
		DriverVehicle responseDriverVehicle = null;
		try {

			String responseJson = mockMvc
					.perform(delete("/api/vehicles/drivervehicles/" + DriverVehicleForTest.getId()).header("Authorization", "Bearer " + token)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

			responseDriverVehicle = gson.fromJson(responseJson, DriverVehicle.class);
			assertEquals(0, responseDriverVehicle.getStatus());
			assertNotNull(responseDriverVehicle.getId());
		} finally {
			if (responseDriverVehicle != null && responseDriverVehicle.getId() != null) {
				driverVehicleRepository.deleteById(responseDriverVehicle.getId());

			}
//			driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
			vehicleRepository.deleteById(VehicleForTest.getId());
		}
	}

	@Test
	public void whenUpdateDriverVehicleWrongDriverVehicleId_thenReturnError() throws Exception {
		// Booking responseBooking = null;
		try {

			mockMvc.perform(delete("/api/vehicles/drivervehicles/123123123").header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		} finally {
			driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
			vehicleRepository.deleteById(VehicleForTest.getId());
		}
	}

	@Test
	public void whenUpdateVehicleWithoutVehicleId_thenReturnError() throws Exception {
		// Booking responseBooking = null;
		try {

			Vehicle VehicleRequest = new Vehicle();
			VehicleRequest.setStatus(1);
			String json = gson.toJson(VehicleRequest);
			mockMvc.perform(put("/api/vehicles").header("Authorization", "Bearer " + token).content(json)
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		} finally {
			driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
			vehicleRepository.deleteById(VehicleForTest.getId());
		}
	}

	@Test
	public void whenDeleteVehicleAndDriverVehicle_thenReturnOkhttprequest() throws Exception {
		DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
		driverVehicleDTO.setDriverid(DriverVehicleForTest.getDriver().getId());
		driverVehicleDTO.setLicenseplate(DriverVehicleForTest.getVehicle().getLicenseplate());
		
		String json = gson.toJson(driverVehicleDTO);
		mockMvc.perform(delete("/api/vehicles").header("Authorization", "Bearer " + token).content(json)
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(200));
		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
		
	}
	
	@Test
	public void whenDeleteVehicleWrongDriveId_thenReturnError() throws Exception {
		DriverVehicleDTO driverVehicleDTO = new DriverVehicleDTO();
		driverVehicleDTO.setDriverid((long)123123123);
		driverVehicleDTO.setLicenseplate("30Z2-12345");
		String json = gson.toJson(driverVehicleDTO);
		mockMvc.perform(delete("/api/vehicles").header("Authorization", "Bearer " + token).content(json)
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(409));
		driverVehicleRepository.deleteById(DriverVehicleForTest.getId());
		vehicleRepository.deleteById(VehicleForTest.getId());
	}
	
}
