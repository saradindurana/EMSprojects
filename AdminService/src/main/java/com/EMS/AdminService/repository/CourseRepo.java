package com.EMS.AdminService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.AdminService.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {

}
