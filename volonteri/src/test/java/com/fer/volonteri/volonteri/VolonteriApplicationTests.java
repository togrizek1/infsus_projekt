package com.fer.volonteri.volonteri;

import com.fer.volonteri.volonteri.entity.ActivityStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolonteriApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestH2Repository testRepo;

	@Test
	void contextLoads() {
	}

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/activity-status");
	}

	@Test
	public void testPostActivityStatus() {
		ActivityStatus activityStatus = new ActivityStatus();
		activityStatus.setName("AWAITING APPROVAL");
		activityStatus.setId(1l);

		ActivityStatus response = restTemplate.postForObject(baseUrl, activityStatus, ActivityStatus.class);

		assertEquals("AWAITING APPROVAL", response.getName());
		assertEquals(1, testRepo.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO aktivnost_status (id_status, naziv) VALUES (4, 'TO BE ORGANIZED')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM aktivnost_status WHERE naziv='TO BE ORGANIZED'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testGetAllStatuses(){
		List<ActivityStatus> activityStatuses = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(1, activityStatuses.size());
		assertEquals(1, testRepo.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO aktivnost_status (id_status, naziv) VALUES (4, 'TO BE ORGANIZED')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM aktivnost_status WHERE naziv='TO BE ORGANIZED'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testUpdateStatus(){

		ActivityStatus activityStatus = new ActivityStatus();
		activityStatus.setName("AWAITING APPROVAL");

		restTemplate.put(baseUrl + "/{id}", activityStatus, 4l);
		ActivityStatus activityStatusFromDB = testRepo.findById(4l).get();

		assertAll(
				() -> assertNotNull(activityStatusFromDB),
				() -> assertEquals("AWAITING APPROVAL", activityStatusFromDB.getName())
		);

	}

	@Test
	@Sql(statements = "INSERT INTO aktivnost_status (id_status, naziv) VALUES (6, 'FINISHED')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteStatus(){
		int recordCount = testRepo.findAll().size();
		assertEquals(1, recordCount);
		restTemplate.delete(baseUrl + "/{id}", 6l);
		assertEquals(0, testRepo.findAll().size());
	}


}
