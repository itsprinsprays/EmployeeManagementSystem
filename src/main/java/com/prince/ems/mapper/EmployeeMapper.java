package com.prince.ems.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetEmployeeResponseDTO;
import com.prince.ems.dto.employee.UpdateEmployeeResponseDTO;
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
	
	public static Page<GetEmployeeResponseDTO> getActiveResponse(Page<Employee> employee) {
		Page<GetEmployeeResponseDTO> dto = employee.map(e -> new GetEmployeeResponseDTO(
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
	
	public static GetEmployeeResponseDTO getEmployeeById(Employee employee) {
		GetEmployeeResponseDTO dto = new GetEmployeeResponseDTO(
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
	
	public static Page<GetEmployeeResponseDTO> getAllEmployeeResponse(Page<Employee> employee) {
		return employee.map(e -> EmployeeMapper.getEmployeeById(e));
	}
	
	public static UpdateEmployeeResponseDTO updateResponse(Employee employee) {
		UpdateEmployeeResponseDTO dto = new UpdateEmployeeResponseDTO();
		
		dto.setID(employee.getId());
		dto.setName(employee.getName());
		dto.setEmail(employee.getEmail());
		dto.setSalary(employee.getSalary());
		dto.setStatus(employee.getStatus());
		dto.setDepartment(employee.getDepartment().getDepartmentId());
		dto.setHireDate(employee.getHireDate());
		dto.setUpdatedAt(employee.getUpdatedAt());
		dto.setCreatedAt(employee.getCreatedAt());
		
		return dto;
		
	}

}
