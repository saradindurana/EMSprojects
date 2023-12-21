package com.EMS.studentService.studentService;

import com.EMS.studentService.entity.Course;
import com.EMS.studentService.entity.Faculty;

public interface AdminService {

	
	Faculty addfaculty(Faculty faculty);
	Faculty updateFaculty(Faculty faculty, int id);
	Faculty removeFaculty (int id); 
	Course addCourse(Course course); 
}
