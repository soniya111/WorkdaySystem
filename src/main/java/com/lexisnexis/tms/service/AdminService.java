package com.lexisnexis.tms.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexisnexis.tms.PasswEncrypt;
import com.lexisnexis.tms.UserNotFoundException;
import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.entity.CourseDetails;

import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.inter.AdminServiceIntr;
import com.lexisnexis.tms.repository.CourseDetailsRepo;

import com.lexisnexis.tms.repository.SubjRepo;
import com.lexisnexis.tms.repository.UserAdminRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AdminService implements AdminServiceIntr{

	@Autowired
	SubjEntity subjEntity;

	@Autowired
	SubjRepo subjRepo;

	@Autowired
	CourseDetails courseDetails;

	@Autowired
	CourseDetailsRepo courseDetailsRepo;

//	@Autowired
//	PasswordEncoder passwordEncoder;

	@Autowired
	UserAdminRepo userAdminRepo;
	
	@Autowired
	PasswEncrypt passwEncrypt;


	public APIResponse addUser(UserAdmin useradmin) throws NoSuchAlgorithmException {

		APIResponse apiResponse = new APIResponse();
		if (userAdminRepo.existsById(useradmin.getUser_id())) {
			apiResponse.setData("user Id already taken");
			return apiResponse;
		} else {
			useradmin.setPassword(passwEncrypt.encryptPass(useradmin.getPassword()));
			userAdminRepo.save(useradmin);
			apiResponse.setData("user added Successfully");
			return apiResponse;
		}

	}

	public APIResponse savefields(SubjEntity fields) {
		APIResponse apiResponse = new APIResponse();

		if (subjRepo.existsById(fields.getField_id())) {
			apiResponse.setData("field Id already taken");
			return apiResponse;
		} else {
			subjRepo.save(fields);
			apiResponse.setData("fields saved succesfully");
			return apiResponse;
		}

	}

	public APIResponse saveCourses(CourseDetails courses) {
		APIResponse apiResponse = new APIResponse();

		if (courseDetailsRepo.existsById(courses.getCourse_id())) {
			apiResponse.setData("course Id already taken");
			return apiResponse;
		} else {
			courseDetailsRepo.save(courses);
			apiResponse.setData("courses saved succesfully");
			return apiResponse;
		}

	}

	public void deleteDataByFieldId(String field_id) throws UserNotFoundException  {
		SubjEntity field = subjRepo.findByFieldId(field_id);
		if (field != null) {
			subjRepo.deleteById(field_id);
		} else {
			
			throw new UserNotFoundException("fieldid not found");
		}
	}
	public void deleteDataByCourseId(String course_id) throws UserNotFoundException {
		CourseDetails courseid = courseDetailsRepo.findByCourseId(course_id);
		if (courseid != null) {
			courseDetailsRepo.deleteById(course_id);
		} else {
			throw new UserNotFoundException("courseid not found" + " " + courseid);
		}
	}
}
