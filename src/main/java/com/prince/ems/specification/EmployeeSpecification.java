package com.prince.ems.specification;

import org.springframework.data.jpa.domain.Specification;

import com.prince.ems.entity.*;

public class EmployeeSpecification {
	
	public static Specification<Employee> hasName(String name) {
		return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
	}
	
	public static Specification<Employee> hasStatus(Status status) {
		return (root, query, cb) -> cb.equal(root.get("status"), status);
	}
	
	public static Specification<Employee> hasDepartment(Long Id) {
		return (root, query, cb) -> cb.equal(root.get("department").get("department_id"), Id);
	}
	
	public static Specification<Employee> betweenSalary(Double min, Double max) {
		return (root, query, cb) -> cb.between(root.get("salary"), min, max);
	}

}
