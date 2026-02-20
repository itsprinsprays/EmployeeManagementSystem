package com.prince.ems.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetAllEmployeeResponseDTO;
import com.prince.ems.entity.Employee;

@Component
public class EmployeeMapper {
	
	public static CreateEmployeeResponseDTO createResponse(Employee employee) {
		CreateEmployeeResponseDTO dto = new CreateEmployeeResponseDTO();
		
		dto.setID(employee.getId());
		dto.setName(employee.getName());
		dto.setEmail(employee.getEmail());
		dto.setSalary(employee.getSalary());
		dto.setStatus(employee.getStatus());
		dto.setDepartment(employee.getDepartment().getDepartmentId());
		dto.setHireDate(employee.getHireDate());
		dto.setCreatedAt(employee.getCreatedAt());
		
		return dto;
	}
	
	public static Page<GetAllEmployeeResponseDTO> getActiveResponse(Page<Employee> employee) {
		Page<GetAllEmployeeResponseDTO> dto = employee.map(e -> new GetAllEmployeeResponseDTO(
				e.getId(),
				e.getName(),
				e.getEmail(),
				e.getSalary(),
				e.getStatus(),
				e.getDepartment().getDepartmentId(),
				e.getHireDate(),
				e.getUpdatedAt(),
				e.getCreatedAt()
				));
		
		return dto;
	}
	
	public static GetAllEmployeeResponseDTO getAllEmployeeById(Employee employee) {
		GetAllEmployeeResponseDTO dto = new GetAllEmployeeResponseDTO(
				employee.getId(),
				employee.getName(),
				employee.getEmail(),
				employee.getSalary(),
				employee.getStatus(),
				employee.getDepartment().getDepartmentId(),
				employee.getHireDate(),
				employee.getUpdatedAt(),
				employee.getCreatedAt()
				);
		return dto;
	}

}
