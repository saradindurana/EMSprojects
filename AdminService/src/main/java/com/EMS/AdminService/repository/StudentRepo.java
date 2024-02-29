package com.EMS.AdminService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.AdminService.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	Optional<Student> findByEmail(String Email_Address);
}
