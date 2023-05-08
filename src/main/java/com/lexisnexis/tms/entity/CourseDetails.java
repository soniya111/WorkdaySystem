package com.lexisnexis.tms.entity;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lexisnexis.tms.bool.BooleanToYNStringConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "CourseDetails")
@Component
@Data
@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class CourseDetails {

	@Id
	private String course_id;

	private String courseName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courseId")
	private List<LearningHistory> coursetaken;

	@ManyToOne()
	@JoinColumn(name = "field_id", referencedColumnName = "field_id")
	private SubjEntity subjEntity;

	private String courseTitle;
	private String courseDescr;
	private int version;
	private String contentType;// Digital Course, Program

	@Convert(converter = BooleanToYNStringConverter.class)
	@Column(name = "EnrollementStatus", length = 2)
	private Boolean enrollementStatus;
}
