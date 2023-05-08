package com.lexisnexis.tms.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lexisnexis.tms.entity.LearningHistory;

@Repository
public interface LearningHistoryRepo extends JpaRepository<LearningHistory, String>{
	

	@Query(value="SELECT course_id FROM Learning_History c WHERE c.user_id = ?1", nativeQuery = true)
	public String findByUserId(String user_id);
	
	@Query(value="SELECT course_id FROM Learning_History c WHERE c.user_id = ?1", nativeQuery = true)
	public List<String> findByUserId1(String user_id);
	
	@Query(value="SELECT hist_id FROM Learning_History c WHERE c.user_id = ?1 AND C.course_id = ?2", nativeQuery = true)
	public String findByLearnId(String user_id,String course_id);
	
	@Query(value="SELECT * FROM Learning_History c WHERE c.user_id = ?1 AND C.course_id = ?2", nativeQuery = true)
	public LearningHistory findforup(String user_id,String course_id);
	
	@Query(value = "SELECT * FROM Learning_History c WHERE c.hist_id = ?1" , nativeQuery = true)
	public  LearningHistory findByLearnIdd(String hist_id);
	
	
	@Query(value = "update LearningHistory l set "
			+ "l.completion_status = ?1, "
			+ "l.completion_Date_Time = ?2 "
			+ "WHERE l.user_id = ?3 AND "
			+ "l.course_id = ?4" , nativeQuery = true)
	public  LearningHistory setComplStatus(String completion_status,LocalDateTime completion_Date_Time,String user_id,String course_id);

	@Query(value = "SELECT * FROM Learning_History c WHERE c.user_id = ?1 AND C.course_id = ?2", nativeQuery = true)
	public Optional<LearningHistory> findByIdd(String user_id,String course_id);
	
	
	
}
