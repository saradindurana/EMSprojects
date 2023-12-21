package com.EMS.studentService.studentService;

import java.util.List;


import com.EMS.studentService.entity.Student;



public interface StudentService {
	
	Student createStudent(Student st);
	Student updateStudent(Student st, String username);
	List<Student> getAllStudent();
	String enrollCourse(String email, int c_id);
}
