package com.lexisnexis.tms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.contoller.AdminUserController;
import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.LearningHistory;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.service.AdminService;
import com.lexisnexis.tms.service.UserService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@RunWith(SpringJUnit4ClassRunner.class)
class ControllersTest {

	private MockMvc mockmvc;

	@InjectMocks
	private AdminUserController adminUserController;

	@Mock
	private AdminService adminService;
	@Mock
	private UserService userservice;

	@BeforeEach
	void setUp() throws Exception {
		mockmvc = MockMvcBuilders.standaloneSetup(adminUserController).build();

	}

	@Test
	public void testAddUsers() throws Exception {
		UserAdmin userAdmin = new UserAdmin();
		APIResponse apiResponse = new APIResponse();
		when(adminService.addUser(userAdmin)).thenReturn(apiResponse);
		ResponseEntity<APIResponse> addUsers = adminUserController.addUsers(userAdmin);
		assertEquals(HttpStatus.OK, addUsers.getStatusCode());
		assertEquals(apiResponse, addUsers.getBody());
	}

	@Test
	public void saveFields() {
		SubjEntity sub = new SubjEntity();
		APIResponse apiResponse = new APIResponse();
		when(adminService.savefields(sub)).thenReturn(apiResponse);
		ResponseEntity<APIResponse> fields = adminUserController.savefields(sub);
		assertEquals(HttpStatus.OK, fields.getStatusCode());
		assertEquals(apiResponse, fields.getBody());

	}

	@Test
	public void saveCourses() {
		CourseDetails courses = new CourseDetails();

		APIResponse apiResponse = new APIResponse();
		when(adminService.saveCourses(courses)).thenReturn(apiResponse);
		ResponseEntity<APIResponse> course = adminUserController.saveCourses(courses);
		assertEquals(HttpStatus.OK, course.getStatusCode());
		assertEquals(apiResponse, course.getBody());
	}

	@Test
	public void saveHistInfo() {

		LearningHistory history = new LearningHistory();
		String userid = "user123";
		String fieldid = "field123";
		String courseid = "course123";
		APIResponse apiResponse = new APIResponse();
		when(userservice.check(userid, fieldid, courseid, history)).thenReturn(apiResponse);
		ResponseEntity<APIResponse> checkinfo = adminUserController.saveHistInfo(userid, fieldid, courseid, history);
		assertEquals(HttpStatus.OK, checkinfo.getStatusCode());
		assertEquals(apiResponse, checkinfo.getBody());
	}

	@Test
	public void showFields() {

		String fieldid1 = "123d";
		String fieldid2 = "4567";
		List<String> indivfiel = new ArrayList<>();
		List<List<String>> fields = new ArrayList<>();
		indivfiel.add(fieldid2);
		indivfiel.add(fieldid1);
		fields.add(indivfiel);
		when(userservice.showFieldss()).thenReturn(fields);
		ResponseEntity<List<List<String>>> s = (ResponseEntity<List<List<String>>>) adminUserController.showFields();
		assertEquals(HttpStatus.OK, s.getStatusCode());
		assertEquals(fields, s.getBody());
	}

	@Test
	public void showCourses() throws UserNotFoundException {
		String fieldid = "field123";
		List<String> co = new ArrayList<>();
		List<List<String>> co1 = new ArrayList<>();
		String coursess1 = "course123";
		String coursess2 = "course1234";
		String coursess3 = "courses1235";
		co.add(coursess1);
		co.add(coursess2);
		co.add(coursess3);
		co1.add(co);
		when(userservice.showCourses(fieldid)).thenReturn(co1);
		ResponseEntity<List<List<String>>> courses = adminUserController.showCourses(fieldid);
		assertEquals(HttpStatus.OK, courses.getStatusCode());
		assertEquals(co1, courses.getBody());

	}

	@Test
	public void deleteDataByUserIdTest() throws UserNotFoundException {
		UserAdmin userAdmin = new UserAdmin();
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData("deleted successfully");
		ResponseEntity<String> del = adminUserController.deleteUserId("so");

		assertEquals(HttpStatus.OK, del.getStatusCode());
		assertEquals(apiResponse.getData(), del.getBody());

	}

	@Test
	public void deleteDataByFieldIdTest() throws UserNotFoundException {
		UserAdmin userAdmin = new UserAdmin();
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData("deleted successfully");
		ResponseEntity<String> del = adminUserController.deleteDataByFieldId("so");

		assertEquals(HttpStatus.OK, del.getStatusCode());
		assertEquals(apiResponse.getData(), del.getBody());

	}

	@Test
	public void deleteDataByCourseIdTest() throws UserNotFoundException {
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData("deleted successfully");
		ResponseEntity<String> del = adminUserController.deleteDataByCourseId("so");

		assertEquals(HttpStatus.OK, del.getStatusCode());
		assertEquals(apiResponse.getData(), del.getBody());

	}

	@Test
	public void deleteDataByHistIdTest() throws UserNotFoundException {
		APIResponse apiResponse = new APIResponse();
		apiResponse.setData("deleted successfully");
		ResponseEntity<String> del = adminUserController.deleteDataByHistId("so", "do");

		assertEquals(HttpStatus.OK, del.getStatusCode());
		assertEquals(apiResponse.getData(), del.getBody());

	}

	@Test
	public void complDate() {
		LearningHistory history = new LearningHistory();
		String histId = "123";
		ResponseEntity<APIResponse> res = adminUserController.completDate(history, histId);
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
}
