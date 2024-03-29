package com.EMS.studentService.studentService;

import java.util.List;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Student;



public interface StudentService {
	
	Student createStudent(Student st);
	String login(int id, String password);
	List<Student> getAllStudent();
	String enrollCourse(String email, int c_id);
	List<Course> getAllCourses();
	Student getStudentByEmail(String email);
}
