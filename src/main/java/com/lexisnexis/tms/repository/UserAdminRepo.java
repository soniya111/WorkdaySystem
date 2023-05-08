package com.lexisnexis.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lexisnexis.tms.entity.UserAdmin;

@Repository
public interface UserAdminRepo extends JpaRepository<UserAdmin, String> {

	@Query(value = "SELECT user_id FROM user_Admin_Table c WHERE c.user_id = ?1", nativeQuery = true)
	public String findByUserId1(String user_id);

	@Query(value = "SELECT * FROM user_Admin_Table c WHERE c.user_id = ?1", nativeQuery = true)
	public UserAdmin findByUserId(String user_id);

}
