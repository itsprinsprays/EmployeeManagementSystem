package com.prince.ems.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.Employee;
import com.prince.ems.entity.Status;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	boolean existsByEmail(String name );
	
	Page<Employee> findByStatus(Status status, Pageable pageable);
	
	boolean existsByEmailAndIdNot(String email, Long Id);

}
