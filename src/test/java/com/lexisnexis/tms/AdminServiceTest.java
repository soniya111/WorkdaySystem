package com.lexisnexis.tms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;

import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.LearningHistory;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.inter.AdminServiceIntr;
import com.lexisnexis.tms.repository.CourseDetailsRepo;
import com.lexisnexis.tms.repository.LearningHistoryRepo;
import com.lexisnexis.tms.repository.SubjRepo;
import com.lexisnexis.tms.repository.UserAdminRepo;
import com.lexisnexis.tms.service.AdminService;
import com.lexisnexis.tms.service.UserService;

import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

	@Mock
	private SubjRepo subjRepo;

	@Mock
	PasswEncrypt passwEncrypt;

	@Mock
	private CourseDetailsRepo courseDetailsRepo;

	@Mock
	private UserAdminRepo userAdmiRepo;

	@InjectMocks
	private AdminService subjService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddUserIfUserIdNotexists() throws NoSuchAlgorithmException {
		UserAdmin user = new UserAdmin();
		user.setUser_id("testuser");
		// user.setPassword("password");

		when(userAdmiRepo.existsById("testuser")).thenReturn(false);

		when(passwEncrypt.encryptPass(user.getPassword())).thenReturn("");

		APIResponse response = subjService.addUser(user);

		assertNotNull(response);
		assertEquals("user added Successfully", response.getData());
	}

	@Test
	public void testAddUserIfUserIdexists() throws NoSuchAlgorithmException {
		UserAdmin user = new UserAdmin();
		user.setUser_id("soniya1");
		user.setPassword("soniya");

		when(userAdmiRepo.existsById("soniya1")).thenReturn(true);

		APIResponse response = subjService.addUser(user);
		assertNotNull(response);
		assertEquals("user Id already taken", response.getData());

	}

	@Test
	public void testSaveFieldsIfFieldIdExists() {

		SubjEntity fields = new SubjEntity();
		fields.setField_id("IT101");
		when(subjRepo.existsById("IT101")).thenReturn(true);

		APIResponse result = subjService.savefields(fields);

		assertNotNull(result);
		assertEquals("field Id already taken", result.getData());
	}

	@Test
	public void testSaveFieldsIfFieldIdDoesNotExist() {

		SubjEntity fields = new SubjEntity();
		fields.setField_id("IT1011");
		when(subjRepo.existsById("IT1011")).thenReturn(false);

		APIResponse result = subjService.savefields(fields);

		assertNotNull(result);
		assertEquals("fields saved succesfully", result.getData());
	}

	@Test
	public void saveCoursesIfCourseIdExists() {

		SubjEntity subb = new SubjEntity();
		subb.setField_id("IT101");
		CourseDetails courses = new CourseDetails();
		courses.setCourse_id("jav101");
		when(courseDetailsRepo.existsById("jav101")).thenReturn(true);
		APIResponse result = subjService.saveCourses(courses);
		assertNotNull(result);
		assertEquals("course Id already taken", result.getData());

	}

	@Test
	public void saveCoursesIfCourseIdNotExists() {

		SubjEntity subb = new SubjEntity();
		subb.setField_id("IT101111");
		CourseDetails courses = new CourseDetails();
		courses.setCourse_id("abcd");
		when(courseDetailsRepo.existsById("abcd")).thenReturn(false);
		APIResponse result = subjService.saveCourses(courses);
		assertNotNull(result);
		assertEquals("courses saved succesfully", result.getData());

	}

	@Test
	public void deleteFieldsIffieldIdExists() throws Exception {
		String fieldId = "field123";

		SubjEntity field = new SubjEntity();
		field.setField_id(fieldId);
		Mockito.when(subjRepo.findByFieldId(fieldId)).thenReturn(field);

		subjService.deleteDataByFieldId(fieldId);

		Mockito.verify(subjRepo).deleteById(fieldId);
	}

	@Test
	public void deleteFieldsIffieldIdNotExists() throws UserNotFoundException {
		String fieldId = "IT1021";

		Mockito.when(subjRepo.findByFieldId(fieldId)).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> subjService.deleteDataByFieldId(fieldId));

		Mockito.verify(subjRepo, Mockito.times(1)).findByFieldId(fieldId);
		Mockito.verify(subjRepo, Mockito.never()).deleteById(Mockito.anyString());

//		
	}

	@Test
	public void deleteCourseIfCourseIdExists() throws UserNotFoundException {
		String courseId = "course123";
		CourseDetails c = new CourseDetails();
		c.setCourse_id(courseId);
		Mockito.when(courseDetailsRepo.findByCourseId(courseId)).thenReturn(c);

		subjService.deleteDataByCourseId(courseId);
		Mockito.verify(courseDetailsRepo).deleteById(courseId);
	}

	@Test
	public void deleteCourseIfCourseIdNotExists() throws Exception {
		String courseId = "course123sd";
		Mockito.when(courseDetailsRepo.findByCourseId(courseId)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> subjService.deleteDataByCourseId(courseId));
		Mockito.verify(courseDetailsRepo, Mockito.times(1)).findByCourseId(courseId);
		Mockito.verify(courseDetailsRepo, Mockito.never()).deleteById(Mockito.anyString());

	}

//
}
