package com.lexisnexis.tms.entity;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lexisnexis.tms.repository.UserAdminRepo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Data
//@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "userAdminTable")
public class UserAdmin {
	



	@Id
	private String user_id;

	private String name;

	private String password;

	private String role;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userId")
	private List<LearningHistory> learnhist;
	
	
	

}
