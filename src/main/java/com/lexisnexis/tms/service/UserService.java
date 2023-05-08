package com.lexisnexis.tms.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexisnexis.tms.UserNotFoundException;
import com.lexisnexis.tms.api.APIResponse;

import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.LearningHistory;
import com.lexisnexis.tms.entity.SubjEntity;

import com.lexisnexis.tms.entity.UserAdmin;
import com.lexisnexis.tms.inter.UserServiceIntr;
import com.lexisnexis.tms.repository.CourseDetailsRepo;
import com.lexisnexis.tms.repository.LearningHistoryRepo;
import com.lexisnexis.tms.repository.SubjRepo;
import com.lexisnexis.tms.repository.UserAdminRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements UserServiceIntr{

	// 1. check if the particular userid has already enrolled the particular course
	// 2. if not enrolled then can enroll otherwise allready enrolled

	@Autowired
	LearningHistory learningHistory;

	@Autowired
	LearningHistoryRepo learningHistoryRepo;

	@Autowired
	CourseDetailsRepo courseDetailsRepo;

	@Autowired
	CourseDetails courseDetails;
	@Autowired
	LearningHistoryRepo historyRepo;

	@Autowired
	SubjRepo subjRepo;

	@Autowired
	SubjEntity subjEntity;

	@Autowired
	AdminService adminService;

	@Autowired
	UserAdmin userAdmin;

	@Autowired
	UserAdminRepo userAdminRepo;

	public List<List<String>> showFieldss() {
		return subjRepo.showFields();

	}

	

	public List<List<String>> showCourses(String field_id) throws UserNotFoundException {
		SubjEntity sub =new SubjEntity();
		sub.setField_id(field_id);
		String f= sub.getField_id();
		if (subjRepo.existsById(f)) {
			return courseDetailsRepo.showCourses(field_id);
		}
		else
		{
			throw new UserNotFoundException("Invalid Field ID ");
		}
		
	}

	// check if already enrolled
	// check fieldid and respective courseid matches
	public APIResponse check(String userid, String fielid, String course_id, LearningHistory lh) {
		

		LearningHistory learningHistory = new LearningHistory();
	    UserAdmin userAdmin = new UserAdmin();
	    SubjEntity subjEntity = new SubjEntity();
	    APIResponse apiResponse = new APIResponse();
	    CourseDetails courseDetails = new CourseDetails();

	    learningHistory.setUserId(userAdmin);
	    learningHistory.getUserId().setUser_id(userid);
	    String useridd = learningHistory.getUserId().getUser_id();

	    learningHistory.setFieldstaken(subjEntity);
	    learningHistory.getFieldstaken().setField_id(fielid);
	    String fieldid = learningHistory.getFieldstaken().getField_id();

	    learningHistory.setCourseId(courseDetails);
	    learningHistory.getCourseId().setCourse_id(course_id);
	    String courseid = learningHistory.getCourseId().getCourse_id();

	    List<String> courseids = courseDetailsRepo.findcourseIdd(fieldid);

	    boolean foundCourse = false;
	    for (String course : courseids) {
	        if (course.equals(courseid)) {
	            foundCourse = true;
	            break;
	        }
	    }
	    if (!foundCourse) {
	        apiResponse.setData("course id or fieldid mismatch");
	        return apiResponse;
	    }

	    List<String> sw = learningHistoryRepo.findByUserId1(useridd);
	    String lastCourse = sw.isEmpty() ? null : sw.get(sw.size() - 1);
	    if (lastCourse != null && lastCourse.equals(courseid)) {
	        apiResponse.setData("already enrolled in that course");
	        return apiResponse;
	    }

	    learningHistoryRepo.save(lh);
	    apiResponse.setData("saved successfully");
	    return apiResponse;
		
		
	}

	public void deleteDataByUserId(String user_id) throws UserNotFoundException {
		UserAdmin user = userAdminRepo.findByUserId(user_id);
		if (user != null) {
			userAdminRepo.deleteById(user_id);
		} else {
			throw new UserNotFoundException("user is not registered" + " " + user_id);
		}
	}
	public void deleteDataByLearnhistId(String user_id, String course_id) throws UserNotFoundException {
		String learninghistId = learningHistoryRepo.findByLearnId(user_id, course_id);
		LearningHistory history = learningHistoryRepo.findByLearnIdd(learninghistId);
		if (history != null) {
			learningHistoryRepo.deleteById(learninghistId);

		} else {
			throw new UserNotFoundException("not enrolled yet" + " " + learninghistId);
		}
	}

	public APIResponse updateHist(LearningHistory l, String histId) {
		APIResponse apiResponse = new APIResponse();

		Optional<LearningHistory> histid = learningHistoryRepo.findById(histId);
		boolean present = histid.isPresent();
		if (present == true) {
			LearningHistory newUser = histid.get();

			LocalDateTime now = LocalDateTime.now();
			newUser.setGrade("pass");
			newUser.setCompletionDateTime(now);
			newUser.setCompletionStatus(l.getCompletionStatus()); //

			learningHistoryRepo.save(newUser);
			apiResponse.setData("User hist updated successfully");
			return apiResponse;
		} else {
			apiResponse.setData("histId is wrong");
		}
		return apiResponse;
	}
//	

}
