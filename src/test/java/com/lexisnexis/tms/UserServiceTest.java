package com.lexisnexis.tms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.LearningHistory;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.repository.CourseDetailsRepo;
import com.lexisnexis.tms.repository.LearningHistoryRepo;
import com.lexisnexis.tms.repository.SubjRepo;
import com.lexisnexis.tms.repository.UserAdminRepo;
import com.lexisnexis.tms.service.AdminService;
import com.lexisnexis.tms.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private SubjRepo subjRepo;

	@Mock
	private CourseDetailsRepo courseDetailsRepo;

	@Mock
	private LearningHistoryRepo learningHistoryRepo;

	@InjectMocks
	private UserService userService;

	@Mock
	private UserAdminRepo userAdminRepo;

	@Test
	void showfields() {
		userService.showFieldss();
		verify(subjRepo).showFields();
	}

	@Test
	public void testShowCoursesExists() throws UserNotFoundException {
		String fieldId = "IT101";
		SubjEntity subjEntity = new SubjEntity();
		subjEntity.setField_id(fieldId);
		List<List<String>> expectedCourses = Arrays
				.asList(Arrays.asList("java", "jav101", "IT101", "java in a nutshell", "java, jee,j2ee", "program"));

		when(subjRepo.existsById(fieldId)).thenReturn(true);
		when(courseDetailsRepo.showCourses(fieldId)).thenReturn(expectedCourses);

		List<List<String>> actualCourses = userService.showCourses(fieldId);

		assertEquals(expectedCourses, actualCourses);
	}

	@Test
	void testShowCoursesWithInvalidFieldId() throws UserNotFoundException {

		String invalidFieldId = "it13";
//		List<List<String>> expectedCourses = Arrays
//				.asList(Arrays.asList("java", "jav101", "it13", "java in a nutshell", "java, jee,j2ee", "program"));

		when(subjRepo.existsById(invalidFieldId)).thenReturn(false);

//		List<List<String>> actualCourses = userService.showCourses(invalidFieldId);
		assertThrows(UserNotFoundException.class, () -> {
			userService.showCourses("it13");
		});
//		assertEquals(expectedCourses, actualCourses);
	}

	@Test
	public void testCheckforIdsFound() {
		LearningHistory learningHistory = new LearningHistory();
		UserAdmin userAdmin = new UserAdmin();
		String userId = "soniya11";
		String fieldId = "IT1011";
		String courseId = "Javaa1";

		userAdmin.setUser_id(userId);

		SubjEntity subjEntity = new SubjEntity();
		subjEntity.setField_id(fieldId);
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setCourse_id(courseId);

		learningHistory.setUserId(userAdmin);
		learningHistory.setFieldstaken(subjEntity);
		learningHistory.setCourseId(courseDetails);

		List<String> courseIds = new ArrayList<>();
		courseIds.add(courseId);

		when(courseDetailsRepo.findcourseIdd(fieldId)).thenReturn(courseIds);

		List<String> courses = new ArrayList<>();
//		courses.add("phpp");
		courses.add(courseId);

		when(learningHistoryRepo.findByUserId1(userId)).thenReturn(courses);

		APIResponse expectedResponse = new APIResponse();
		expectedResponse.setData("already enrolled in that course");

		APIResponse actualResponse = userService.check(userId, fieldId, courseId, learningHistory);

		assertEquals(expectedResponse.getData(), actualResponse.getData());

	}

	@Test
	public void testCheckforIdsNotFound() {

		String userId = "user123";
		String fieldId = "field123";
		String courseId = "course123";
		String courseId1 = "course345";
		LearningHistory lh = new LearningHistory();

		List<String> courseIds = new ArrayList<>();
		courseIds.add(courseId);
		
		List<String> courseIds1 = new ArrayList<>();
		courseIds.add(courseId1);
		
		Mockito.when(courseDetailsRepo.findcourseIdd(fieldId)).thenReturn(courseIds);

		List<String> enrolledCourseIds = new ArrayList<>();
		enrolledCourseIds.add("enrolledCourseId123");
		Mockito.when(learningHistoryRepo.findByUserId1(userId)).thenReturn(courseIds1);
		APIResponse apiResponse = userService.check(userId, fieldId, courseId, lh);

		Mockito.verify(courseDetailsRepo).findcourseIdd(fieldId);
		Mockito.verify(learningHistoryRepo).findByUserId1(userId);
		Mockito.verify(learningHistoryRepo).save(lh);

		Assert.assertEquals("saved successfully", apiResponse.getData());
	}
	
//	@Test
//	public void check() {
//
//	    // Create a mock learning history object
//	    LearningHistory lh = new LearningHistory();
//
//	    // Create a mock API response object
//	    APIResponse expectedResponse = new APIResponse();
//	    expectedResponse.setData("saved successfully");
//
//	    String userId = "user123";
//		String fieldId = "field123";
//		lh.setUserId(userId);
//	    // Create a mock course details object
//	    CourseDetails courseDetails = new CourseDetails();
//	    courseDetails.setCourse_id("C001");
//
//	    // Mock the repository method to return a list containing the course ID
//	    List<String> courseIds = new ArrayList<String>();
//	    courseIds.add("C001");
//	    when(courseDetailsRepo.findcourseIdd("")).thenReturn(courseIds);
//
//	    // Mock the repository method to return an empty list of learning history records
//	    when(learningHistoryRepo.findByUserId1(anyString())).thenReturn(new ArrayList<String>());
//
//	    // Call the check() method with the mock inputs
//	    APIResponse actualResponse = userService.check("U001", "F001", "C001", lh);
//
//	    // Verify that the repository method was called with the correct parameters
//	    verify(learningHistoryRepo, times(1)).save(lh);
//
//	    // Verify that the actual response matches the expected response
//	    assertEquals(expectedResponse, actualResponse);
//	}
//
////	@Test
////	public void testCheckforFieldIdCourseIdMismatch() {
////		String userId = "soniya1";
////		String fieldId = "IT101";
////		String courseId = "Selimium101";
////		LearningHistory lh = new LearningHistory();
////
////		APIResponse apiResponse = userService.check(userId, fieldId, courseId, lh);
////
////		assertEquals("course id or fieldid mismatch", apiResponse.getData());
////	}


	@Test
	public void testDeleteDataByUserId() throws UserNotFoundException {

		UserAdmin user = new UserAdmin();
		user.setUser_id("12345");
		Mockito.when(userAdminRepo.findByUserId("12345")).thenReturn(user);

		userService.deleteDataByUserId("12345");

		Mockito.verify(userAdminRepo, Mockito.times(1)).deleteById("12345");
	}

	@Test
	public void testDeleteDataByUserId_UserNotFoundException() throws UserNotFoundException {

		Mockito.when(userAdminRepo.findByUserId("12345")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> userService.deleteDataByUserId("12345"));
	}

	@Test
	public void testDeleteDataByLearnhistId() throws UserNotFoundException {

		String userId = "user123";
		String courseId = "course456";
		int learninghistId = 123;
		String d = String.valueOf(learninghistId);
		LearningHistory history = new LearningHistory();
		history.setHistId(learninghistId);
		Mockito.when(learningHistoryRepo.findByLearnId(userId, courseId)).thenReturn(d);
		Mockito.when(learningHistoryRepo.findByLearnIdd(d)).thenReturn(history);

		userService.deleteDataByLearnhistId(userId, courseId);

		Mockito.verify(learningHistoryRepo, Mockito.times(1)).findByLearnId(userId, courseId);
		Mockito.verify(learningHistoryRepo, Mockito.times(1)).findByLearnIdd(d);
		Mockito.verify(learningHistoryRepo, Mockito.times(1)).deleteById(d);
	}

	@Test
	public void testDeleteDataByLearnhistIdUserNotFound() {

		String userId = "unknown";
		String courseId = "course456";
		Mockito.when(learningHistoryRepo.findByLearnId(userId, courseId)).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> userService.deleteDataByLearnhistId(userId, courseId));
		Mockito.verify(learningHistoryRepo, Mockito.times(1)).findByLearnId(userId, courseId);
		Mockito.verify(learningHistoryRepo, Mockito.never()).findByLearnIdd(Mockito.anyString());
		Mockito.verify(learningHistoryRepo, Mockito.never()).deleteById(Mockito.anyString());
	}

	@Test
	public void testDeleteDataByLearnhistIdLearninghistNotFound() {
		String userId = "user123";
		String courseId = "course456";
		String learninghistId = "unknown";
		Mockito.when(learningHistoryRepo.findByLearnId(userId, courseId)).thenReturn(learninghistId);
		Mockito.when(learningHistoryRepo.findByLearnIdd(learninghistId)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userService.deleteDataByLearnhistId(userId, courseId));
		Mockito.verify(learningHistoryRepo, Mockito.times(1)).findByLearnId(userId, courseId);
		Mockito.verify(learningHistoryRepo, Mockito.times(1)).findByLearnIdd(learninghistId);
		Mockito.verify(learningHistoryRepo, Mockito.never()).deleteById(Mockito.anyString());
	}

	@Test
	public void testUpdateHist() {

		LearningHistory learningHistory = new LearningHistory();
		learningHistory.setCompletionStatus("completed");

		APIResponse expectedResponse = new APIResponse();
		expectedResponse.setData("User hist updated successfully");

		LearningHistory existingHist = new LearningHistory();
		existingHist.setHistId(123);
		existingHist.setGrade("fail");
		existingHist.setCompletionDateTime(LocalDateTime.now().minusDays(1));
		existingHist.setCompletionStatus("in-progress");

		Mockito.when(learningHistoryRepo.findById("123")).thenReturn(java.util.Optional.of(existingHist));

		APIResponse actualResponse = userService.updateHist(learningHistory, "123");

		Mockito.verify(learningHistoryRepo).findById("123");
		Mockito.verify(learningHistoryRepo).save(Mockito.any(LearningHistory.class));

		assertEquals(expectedResponse.getData(), actualResponse.getData());
	}

	@Test
	public void testUpdateHistHistIdNotFound() {

		LearningHistory l = new LearningHistory();
		String histId = "123";

		APIResponse apiResponse = userService.updateHist(l, histId);

		assertEquals("histId is wrong", apiResponse.getData());
	}

}
