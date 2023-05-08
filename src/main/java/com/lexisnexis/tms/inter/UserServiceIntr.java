package com.lexisnexis.tms.inter;

import java.util.List;

import com.lexisnexis.tms.UserNotFoundException;
import com.lexisnexis.tms.api.APIResponse;
import com.lexisnexis.tms.entity.LearningHistory;

public interface UserServiceIntr {

	public List<List<String>> showFieldss();
	
	public APIResponse check(String userid, String fielid, String course_id, LearningHistory lh);
	public void deleteDataByUserId(String user_id) throws UserNotFoundException;
	public List<List<String>> showCourses(String field_id) throws UserNotFoundException;
	public void deleteDataByLearnhistId(String user_id, String course_id) throws UserNotFoundException;
	public APIResponse updateHist(LearningHistory l, String histId);
}
