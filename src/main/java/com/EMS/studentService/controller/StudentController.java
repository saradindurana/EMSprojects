package com.EMS.studentService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.studentService.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService ;
	
	@GetMapping("/status")
	public String status() {
		return "Hey I am UP!!";
	}
	@GetMapping("/viewCourses")
	public ResponseEntity<List<Course>> viewCourses(){
		
		List<Course> courses = this.studentService.getAllCourses();
		 return ResponseEntity.ok(courses);
		
	}
	@PostMapping("/register")
	public ResponseEntity<Student> saveStudent(@RequestBody Student st){
		
		Student savedStudent = this.studentService.createStudent(st);
		return new ResponseEntity<Student>(savedStudent, HttpStatus.CREATED) ;
		
	}
	@PutMapping("/enroll/{std_id}/{c_id}")
	public ResponseEntity<String> enroll(@PathVariable int std_id,@PathVariable int c_id) {
		String result=this.studentService.enrollCourse(std_id, c_id);
		return ResponseEntity.ok(result);
	}
	
}
