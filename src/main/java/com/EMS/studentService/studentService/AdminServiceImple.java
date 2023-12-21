package com.EMS.studentService.studentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Faculty;
import com.EMS.studentService.entity.Student;
import com.EMS.studentService.repository.CourseRepo;
import com.EMS.studentService.repository.FacultyRepo;

@Service

public class AdminServiceImple implements AdminService {

	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private FacultyRepo facultyRepo;
	
	@Override
	public Faculty addfaculty(Faculty faculty) {
		Faculty savedfaculty= this.facultyRepo.save(faculty);
		return savedfaculty;
	}

	@Override
	public Faculty updateFaculty(Faculty faculty, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeFaculty(int id) {
		facultyRepo.deleteById(id);
		return "Done";
	}

	@Override
	public Course addCourse(Course course) {
		Course savedCourse=courseRepo.save(course);
		return savedCourse;
	}

	@Override
	public String assignCourse(int f_id, int c_id) {
		Faculty faculty = facultyRepo.findById(f_id).orElseThrow();
        Course course = courseRepo.findById(c_id).orElseThrow();
        course.setFaculty(faculty);
        courseRepo.save(course);
        return "Assigned";
	}

}
