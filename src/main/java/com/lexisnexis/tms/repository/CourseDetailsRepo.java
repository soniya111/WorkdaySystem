package com.lexisnexis.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.UserAdmin;

@Repository
public interface CourseDetailsRepo extends JpaRepository<CourseDetails, String>{

	@Query(value = "SELECT  course_name,course_id,field_id,course_title,course_Descr,content_Type  FROM Course_Details c WHERE c.field_id = ?1" , nativeQuery = true)
	public List<List<String>> showCourses(String field_id);
	
	@Query(value="SELECT course_id FROM Course_Details c WHERE c.field_id = ?1", nativeQuery = true)
	public List<String> findcourseIdd(String field_id);
	
	@Query(value = "SELECT * FROM Course_Details c WHERE c.course_id = ?1" , nativeQuery = true)
	public  CourseDetails findByCourseId(String course_id);

}
