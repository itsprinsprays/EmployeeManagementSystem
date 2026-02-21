package com.prince.ems.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prince.ems.entity.Department;
import com.prince.ems.entity.Status;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{
	
	boolean existsByName(String name);
	
	Page<Department> findByStatus(Status status, Pageable pageable);		
}
