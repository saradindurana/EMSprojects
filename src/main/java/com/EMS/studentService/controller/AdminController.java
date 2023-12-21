package com.EMS.studentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.studentService.AdminService;
import com.EMS.studentService.studentService.StudentService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentService studentService ;
	@Autowired
	private AdminService adminService;
	@GetMapping("/status")
	public String status() {
		return "i am ok";
	}
	
	@PostMapping("/addCourse")
	public ResponseEntity<Course> addCourse(@RequestBody Course c){
		Course savedCourse = this.adminService.addCourse(c);
		return new ResponseEntity<Course>(savedCourse, HttpStatus.CREATED) ;
		
		
	}
}