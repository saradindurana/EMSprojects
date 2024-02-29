package com.EMS.AdminService.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.EMS.AdminService.entity.Course;
import com.EMS.AdminService.entity.Faculty;
import com.EMS.AdminService.repository.CourseRepo;
import com.EMS.AdminService.repository.FacultyRepo;

import org.springframework.kafka.support.SendResult;

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
	 public ResponseEntity<Faculty> updateFaculty(Faculty faculty, int id) {
        Optional<Faculty> facultyOptional = facultyRepo.findById(id);
        if (facultyOptional.isPresent()) {
            Faculty existingFaculty = facultyOptional.get();
            existingFaculty.setF_name(faculty.getF_name());
            existingFaculty.setContact_no(faculty.getContact_no());
            return new ResponseEntity<>(facultyRepo.save(existingFaculty), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

	@Override
	 public ResponseEntity<String> removeFaculty(int id) {
        Optional<Faculty> facultyOptional = facultyRepo.findById(id);
        if (facultyOptional.isPresent()) {
            facultyRepo.deleteById(id);
            return new ResponseEntity<>("Faculty with ID " + id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Faculty with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

	@Override
	  public ResponseEntity<Course> addCourse(Course course) {
        Course savedCourse = courseRepo.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

	@Override
	  public ResponseEntity<String> assignCourse(int f_id, int c_id) {
        Optional<Faculty> facultyOptional = facultyRepo.findById(f_id);
        Optional<Course> courseOptional = courseRepo.findById(c_id);
        if (facultyOptional.isPresent() && courseOptional.isPresent()) {
            Faculty faculty = facultyOptional.get();
            Course course = courseOptional.get();
            course.setFaculty(faculty);
            courseRepo.save(course);
            return new ResponseEntity<>("Course with ID " + c_id + " has been assigned to faculty with ID " + f_id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Either faculty with ID " + f_id + " or course with ID " + c_id + " not found", HttpStatus.NOT_FOUND);
        }
	}

}
