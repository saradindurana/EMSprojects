package com.EMS.studentService.studentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Faculty;
import com.EMS.studentService.repository.CourseRepo;

@Service

public class AdminServiceImple implements AdminService {

	
	@Autowired
	private CourseRepo courseRepo;
	@Override
	public Faculty addfaculty(Faculty faculty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Faculty updateFaculty(Faculty faculty, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Faculty removeFaculty(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course addCourse(Course course) {
		Course savedCourse=courseRepo.save(course);
		return savedCourse;
	}

}
