package com.EMS.studentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.studentService.entity.Faculty;

public interface AdminRepo extends JpaRepository<Faculty, Integer>{
	
}
