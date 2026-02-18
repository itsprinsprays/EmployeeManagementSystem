package com.prince.ems.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.entity.Employee;

@Component
public class EmployeeMapper {
	
	public static CreateEmployeeResponseDTO createResponse(Employee employee) {
		CreateEmployeeResponseDTO dto = new CreateEmployeeResponseDTO();
		
		dto.setID(employee.getID());
		dto.setName(employee.getName());
		dto.setEmail(employee.getEmail());
		dto.setSalary(employee.getSalary());
		dto.setStatus(employee.getStatus());
		dto.setDepartment(employee.getDepartment().getDepartmentId());
		dto.setHireDate(employee.getHireDate());
		dto.setCreatedAt(employee.getCreatedAt());
		
		return dto;
	}

}
