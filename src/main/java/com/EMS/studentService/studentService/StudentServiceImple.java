package com.EMS.studentService.studentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.repository.CourseRepo;
import com.EMS.studentService.repository.StudentRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service

public class StudentServiceImple implements StudentService{

	
	@Autowired
	private StudentRepo studentRepo;
	
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public Student createStudent(Student st) {
		System.out.println(st.getEmail_Address());
		Student savedStudent= this.studentRepo.save(st);
		return savedStudent;
	}

	@Override
	public Student updateStudent(Student st, String userName) {
		
//		Do this later
		Student student = this.studentRepo.findById(userName).orElseThrow();
		return student;
	}

	@Override
	public List<Student> getAllStudent() {
		
		return studentRepo.findAll();
	}

	@Override
	  @Transactional
	    public String enrollCourse(String studentId, int courseId) {
	        // Retrieve the student and course entities
	        Student student = studentRepo.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));

	        Course course = courseRepo.findById(courseId)
	                .orElseThrow(() -> new EntityNotFoundException("Course with id " + courseId + " not found"));

	        
	        if (student.getCourses().contains(course)) {
	            throw new IllegalStateException("Student is already enrolled in the course");
	        }

	        student.getCourses().add(course);
	        course.getStudents().add(student);
	        studentRepo.save(student);
	        courseRepo.save(course);
	        return "enrolled";
	    }

}
