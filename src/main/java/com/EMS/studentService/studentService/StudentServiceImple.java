package com.EMS.studentService.studentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.repository.CourseRepo;
import com.EMS.studentService.repository.StudentRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;


@Service

public class StudentServiceImple implements StudentService{
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private StudentRepo studentRepo;
	
	
	@Autowired
	private CourseRepo courseRepo;
	
	
	
//	create or register Student
	@Override
	public Student createStudent(Student st) {
		st.setPassword(passwordEncoder.encode(st.getPassword()));
		Student savedStudent= this.studentRepo.save(st);
		return savedStudent;
	}


	


	@Override
	  @Transactional
	    public String enrollCourse(String email, int c_id) {

		WebClient webClient = WebClient.create();
        Mono<String> status = webClient.post()
                .uri("http://localhost:8040/admin/enroll/{email}/{c_id}", email,c_id)
                .retrieve()
                .bodyToMono(String.class);
        status.subscribe(student -> System.out.println(student));
        return status.map(student -> student).block();
	    }

	@Override
	public List<Course> getAllCourses() {
		
		List <Course> courses=courseRepo.findAll();
		return courses;
	}

	@Override
	public String login(int id, String password) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public List<Student> getAllStudent() {
		List<Student> students=studentRepo.findAll();
		return students;
	}





	@Override
	public Student getStudentByEmail(String email) {
		return studentRepo.findByEmail(email).orElseThrow();
	}

}
