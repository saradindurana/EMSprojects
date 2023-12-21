package com.EMS.studentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EMS.studentService.entity.Faculty;

@Repository

public interface FacultyRepo extends JpaRepository<Faculty, Integer>{
	

}
