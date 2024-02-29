package com.EMS.studentService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.repository.StudentRepo;
import com.EMS.studentService.studentService.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {
	
	@Autowired
	private StudentService studentService ;
	
	@GetMapping("/status")
	public String status() {
		return "Hey I am UP!!";
	}
	@GetMapping("/viewCourses")
	public ResponseEntity<List<Course>> viewCourses(){
		WebClient webClient = WebClient.create();
		Mono<List<Course>> courses = webClient.get()
		        .uri("http://localhost:8040/admin/allcourses")
		        .retrieve()
		        .bodyToMono(new ParameterizedTypeReference<List<Course>>() {});
		return courses.map(courseList -> ResponseEntity.ok().body(courseList))
		        .defaultIfEmpty(ResponseEntity.notFound().build()).block();
		
	}
	@PostMapping("/register")
	public ResponseEntity<Student> saveStudent(@RequestBody Student st){
		
		Student savedStudent = this.studentService.createStudent(st);
		return new ResponseEntity<Student>(savedStudent, HttpStatus.CREATED) ;
		
	}
	@PostMapping("/enroll/{email}/{c_id}")
	public ResponseEntity<String> enroll(@PathVariable String email,@PathVariable int c_id) {
		String result=this.studentService.enrollCourse(email, c_id);
		return ResponseEntity.ok(result);
	}
	@GetMapping("/getStudent/{email}")
	public Mono <ResponseEntity<Student>> getStudent(@PathVariable String email){
//		System.out.println("EMAIL IS  " + email);
		
		 return Mono.justOrEmpty(studentService.getStudentByEmail(email))
				 .map(student->ResponseEntity.ok(student))
				 .defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
}
