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
	
	
	
//	create or register Student
	@Override
	public Student createStudent(Student st) {
		Student savedStudent= this.studentRepo.save(st);
		return savedStudent;
	}


	


	@Override
	  @Transactional
	    public String enrollCourse(int studentId, int courseId) {
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

}
