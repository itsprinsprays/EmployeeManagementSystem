package com.prince.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
