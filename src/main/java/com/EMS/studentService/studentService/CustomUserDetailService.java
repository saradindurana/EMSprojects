package com.EMS.studentService.studentService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EMS.studentService.entity.Student;
import com.EMS.studentService.repository.StudentRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private StudentRepo studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username).orElseThrow();
        if (student == null) {
            throw new UsernameNotFoundException("Student not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                student.getUsername(),
                student.getPassword(),
                new ArrayList<>()
        );
    }
}

