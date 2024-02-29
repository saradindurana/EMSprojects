package com.EMS.AdminService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EMS.AdminService.entity.Faculty;

@Repository

public interface FacultyRepo extends JpaRepository<Faculty, Integer>{
	

}
