package com.lexisnexis.tms.inter;

import java.security.NoSuchAlgorithmException;

import com.lexisnexis.tms.UserNotFoundException;
import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;

public interface AdminServiceIntr {

	public APIResponse addUser(UserAdmin useradmin) throws NoSuchAlgorithmException;
	public APIResponse savefields(SubjEntity fields);
	public APIResponse saveCourses(CourseDetails courses);
	public void deleteDataByFieldId(String field_id) throws UserNotFoundException;
	public void deleteDataByCourseId(String course_id) throws UserNotFoundException;
}
