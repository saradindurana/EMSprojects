package com.EMS.AdminService.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.EMS.AdminService.entity.Course;
import com.EMS.AdminService.entity.Faculty;
import com.EMS.AdminService.entity.Student;
import com.EMS.AdminService.repository.CourseRepo;
import com.EMS.AdminService.repository.StudentRepo;
import com.EMS.AdminService.service.AdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private StudentRepo studentRepo;
	
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	//FOR ENROLLING STUDENT
	@PostMapping("/enroll/{email}/{c_id}")
	public String enrollSTudent(@PathVariable String email, @PathVariable int c_id) {
		// Check if the student exists in the database
		Optional<Student> existingSt = studentRepo.findByEmail(email);
		Course course = courseRepo.findById(c_id).orElseThrow();
			    

		if (existingSt.isPresent()) {
		    // The student already exists, so update their record
		    Student updatedStudent = existingSt.get();
		    if (updatedStudent.getCourses().contains(course)) {
	            throw new IllegalStateException("Student is already enrolled in the course");
	        }
		    else {
		    	
		    
		    updatedStudent.getCourses().add(course);
	        course.getStudents().add(updatedStudent);
	        
	        studentRepo.save(updatedStudent);
	        courseRepo.save(course);
		    }
		      return "EXISTING STUDENT!!, Course added";
		}
		
		else {
		    // The student doesn't exist, so create a new record for them
			Student newStudent = getStudent(email);
			newStudent.getCourses().add(course);
			course.getStudents().add(newStudent);
			studentRepo.save(newStudent);
			courseRepo.save(course);
			
			return "new Student enrolled and course added";
		}
  
		
	}
	
	@GetMapping("/allcourses")
	public Mono<ResponseEntity<List<Course>>> getAllcourses(){
		
		 return Mono.justOrEmpty(courseRepo.findAll())
				 .map(student->ResponseEntity.ok(student))
				 .defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
//	Adding new Course
	
	@PostMapping("/addCourse")
	public ResponseEntity<Course> addCourse(@RequestBody Course course){
		kafkamsg("Hey! Good news!! " + course.getC_name()+" course added, enroll now");
		return this.adminService.addCourse(course) ;
	}
	
	//adding Faculty
	@PostMapping("/addFaculty")
	public ResponseEntity<Faculty> addfaculty(@RequestBody Faculty faculty){
		Faculty savedFaculty = this.adminService.addfaculty(faculty);
		return new ResponseEntity<Faculty>(savedFaculty, HttpStatus.CREATED) ;
	}
	
	//update Faculty
    @PutMapping("/updateFaculty/{id}")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty, @PathVariable int id) {
        return adminService.updateFaculty(faculty, id);
    }

	//delete faculty
	  @DeleteMapping("/deleteFaculty/{id}")
	    public ResponseEntity<String> removeFaculty(@PathVariable int id) {
	        return adminService.removeFaculty(id);
	    }
	  
	  //assigning course to faculty
	  @PostMapping("/assign/{f_id}/{c_id}")
	    public ResponseEntity<String> assignCourse(@PathVariable int f_id, @PathVariable int c_id) {
	        return adminService.assignCourse(f_id, c_id);
	    }
	  
	 
	  
	  
	  @GetMapping("/kafkaMsg")
	  public String kafkaStatus() {
		  try {
			  
			  kafkamsg("kafka running");
			  return "kafka running";
		  }
		  catch(Exception e) {
			  return "something not working";
		  }
	  }
	  
	  
	  
	  
//	  Helper methods   
	  
	  public void kafkamsg(String msg) {
		  CompletableFuture<SendResult<String, Object>> future=template.send("course",msg);
	        future.whenComplete((result,ex)->{
	        	if(ex==null) {
	        		System.out.println("msg sent with offset" + result.getRecordMetadata().offset());
	        	}
	        	else {
	        		System.out.println("Unable to send msg " + ex.getMessage());
	        	}
	        });
	  }
	
	//helper method for enrolling student
    public Student getStudent(String std_id) {
        WebClient webClient = WebClient.create();
        Mono<Student> studentMono = webClient.get()
                .uri("http://localhost:8090/student/getStudent/{std_id}", std_id)
                .retrieve()
                .bodyToMono(Student.class);
        studentMono.subscribe(student -> System.out.println(student));
        return studentMono.map(student -> student).block();

}
}
