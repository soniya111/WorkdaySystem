package com.lexisnexis.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lexisnexis.tms.entity.CourseDetails;
import com.lexisnexis.tms.entity.SubjEntity;
import com.lexisnexis.tms.entity.UserAdmin;

@Repository
public interface SubjRepo extends JpaRepository<SubjEntity,String>{
	
	public String findByFieldName(String fieldName);	
	
	boolean existsById(String field_id);
	
	@Query(value="SELECT field_id, field_name FROM Courses c", nativeQuery = true)
	public List<List<String>> showFields();
	
	@Query(value="SELECT field_id FROM Courses c WHERE c.field_id =?1", nativeQuery = true)
	public String findfieldId(String field_id);
	
	
	@Query(value = "SELECT * FROM Courses c WHERE c.field_id = ?1" , nativeQuery = true)
	public  SubjEntity findByFieldId(String field_id);

	
	
}
