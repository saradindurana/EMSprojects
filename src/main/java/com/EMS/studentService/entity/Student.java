package com.EMS.studentService.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	private String Email_Address;
	private String Name;
	private String Password;
	private String Address;
	private String State;
	private String Country;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Student_ID;
	private String Contact_No;
	private Date Date_of_Birth;
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "Student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    @ManyToMany
    @JoinTable(name="user_role")
	private Set<Role> roles = new HashSet<>();
}
