package com.EMS.AdminService.service;

import org.springframework.http.ResponseEntity;

import com.EMS.AdminService.entity.Course;
import com.EMS.AdminService.entity.Faculty;

public interface AdminService {

	
	Faculty addfaculty(Faculty faculty);
	ResponseEntity<Faculty> updateFaculty(Faculty faculty, int id);
	ResponseEntity<String> removeFaculty (int id); 
	ResponseEntity<Course> addCourse(Course course); 
	ResponseEntity<String> assignCourse(int f_id, int c_id);
}
