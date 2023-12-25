package com.EMS.studentService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EMS.studentService.entity.Student;


@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByEmail(String Email_Address);

}
