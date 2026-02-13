package com.prince.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.Department;


public interface DepartmentRepository extends JpaRepository<Department,Long>{
	boolean existsByName(String name);
		
}
