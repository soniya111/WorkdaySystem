package com.lexisnexis.tms.contoller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexisnexis.tms.PasswEncrypt;
import com.lexisnexis.tms.UserNotFoundException;
import com.lexisnexis.tms.api.APIResponse;

import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.LearningHistory;
//import com.lexisnexis.tms.entity.FieldCourses;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.inter.AdminServiceIntr;
import com.lexisnexis.tms.inter.UserServiceIntr;
import com.lexisnexis.tms.repository.CourseDetailsRepo;
import com.lexisnexis.tms.repository.LearningHistoryRepo;
import com.lexisnexis.tms.repository.SubjRepo;
import com.lexisnexis.tms.service.AdminService;
import com.lexisnexis.tms.service.UserService;

@RestController
@RequestMapping("/tms/api/work")
public class AdminUserController {

	// get the subjentity fields and course details for admin in postman and store
	// them in db
	@Autowired
	SubjRepo subjRepo;

	@Autowired
	CourseDetails courseDetails;

	@Autowired
	CourseDetailsRepo courseDetailsRepo;

	@Autowired
	AdminServiceIntr adminService;

	@Autowired
	UserServiceIntr userService;

	@Autowired
	LearningHistoryRepo historyRepo;

	@PostMapping("/addUser")
	public ResponseEntity<APIResponse> addUsers(@RequestBody UserAdmin userAdmin) throws NoSuchAlgorithmException {
		APIResponse apiResponse = adminService.addUser(userAdmin);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
	}

	@PostMapping("/savefields")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<APIResponse> savefields(@RequestBody SubjEntity fields) {
		APIResponse apiResponse = adminService.savefields(fields);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

	}

	@PostMapping("/savecourses")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<APIResponse> saveCourses(@RequestBody CourseDetails courses) {
		APIResponse apiResponse = adminService.saveCourses(courses);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

	}

	@PostMapping("/saveHistInfo/{user_id}/{field_id}/{course_id}")
	public ResponseEntity<APIResponse> saveHistInfo(@PathVariable(value = "user_id") String user_id,
			@PathVariable(value = "field_id") String field_id, @PathVariable(value = "course_id") String course_id,
			@RequestBody LearningHistory hist) {

		APIResponse apiResponse = userService.check(user_id, field_id, course_id, hist);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

	}

	@GetMapping("/showFields") // show id and name
	public ResponseEntity<List<List<String>>> showFields() {
		List<List<String>> showFieldss = userService.showFieldss();
		return new ResponseEntity<List<List<String>>>(showFieldss, HttpStatus.OK);
	}

	@GetMapping("/showCourses/{field_id}") // show id and name
	public ResponseEntity<List<List<String>>> showCourses(@PathVariable String field_id) throws UserNotFoundException {
		List<List<String>> showCourses = userService.showCourses(field_id);
		return new ResponseEntity<List<List<String>>>(showCourses, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{user_id}")
	public ResponseEntity<String> deleteUserId(@PathVariable(value = "user_id") String user_id)
			throws UserNotFoundException {
		userService.deleteDataByUserId(user_id);
		String delete = "deleted successfully";
		return new ResponseEntity<String>(delete, HttpStatus.OK);

	}

	@DeleteMapping("/deleteFieldId/{field_id}")
	public ResponseEntity<String> deleteDataByFieldId(@PathVariable(value = "field_id") String field_id)
			throws UserNotFoundException {
		adminService.deleteDataByFieldId(field_id);
		String delete = "deleted successfully";
		return new ResponseEntity<String>(delete, HttpStatus.OK);
	}

	@DeleteMapping("/deleteCourseId/{course_id}")
	public ResponseEntity<String> deleteDataByCourseId(@PathVariable(value = "course_id") String course_id)
			throws UserNotFoundException {
		adminService.deleteDataByCourseId(course_id);
		String delete = "deleted successfully";
		return new ResponseEntity<String>(delete, HttpStatus.OK);
	}

	@DeleteMapping("/deleteHistId/{user_id}/{course_id}")
	public ResponseEntity<String> deleteDataByHistId(@PathVariable(value = "user_id") String user_id,
			@PathVariable(value = "course_id") String course_id) throws UserNotFoundException {
		userService.deleteDataByLearnhistId(user_id, course_id);
		String delete = "deleted successfully";
		return new ResponseEntity<String>(delete, HttpStatus.OK);
	}

	@PutMapping("/completiondate/{hist_id}")
	public ResponseEntity<APIResponse> completDate(@RequestBody LearningHistory hist,
			@PathVariable(value = "hist_id") String hist_id) {

		APIResponse apiResponse = userService.updateHist(hist, hist_id);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);

	}

}
