package com.lexisnexis.tms.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.lexisnexis.tms.bool.BooleanToYNStringConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LearningHistory")
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Component
public class LearningHistory {
	
	@Id
	@GeneratedValue
	private int histId;
//	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private UserAdmin userId;
	
	
	@ManyToOne()
	@JoinColumn(name = "field_id")
	private SubjEntity fieldstaken;
	
	@ManyToOne()
	@JoinColumn(name = "course_id")
	private CourseDetails courseId;

	private String regStatus;
	
	@Column
	@CreationTimestamp
	private LocalDateTime dateEnrolled;
	
	private String completionStatus;
	
	@Column
	@CreationTimestamp
	private LocalDateTime completionDateTime;
	
	private String attendanceStatus;
	
	private String grade;
	
	private int score;
	
	private String recordType;
	

}
