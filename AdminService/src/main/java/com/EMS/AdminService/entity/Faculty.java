package com.EMS.AdminService.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
	@Id
	private int f_id;
	private String f_name;
	private String contact_no;
	
	 @OneToMany(mappedBy = "faculty")
	    private List<Course> courses;
	
}
