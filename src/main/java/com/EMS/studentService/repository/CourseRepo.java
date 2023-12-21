package com.EMS.studentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.studentService.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {

}
