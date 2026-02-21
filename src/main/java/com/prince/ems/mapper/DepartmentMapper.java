package com.prince.ems.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.prince.ems.dto.department.CreateDepartmentResponseDTO;
import com.prince.ems.dto.department.DepartmentResponseDTO;
import com.prince.ems.entity.Department;


@Component
public class DepartmentMapper {
	
	public static DepartmentResponseDTO toResponse(Department department, String statusMessage) {
		DepartmentResponseDTO dto = new DepartmentResponseDTO();
		
		dto.setID(department.getDepartmentId());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setStatus(department.getStatus());
		dto.setCreatedAt(department.getCreatedAt());
		dto.setUpdatedAt(department.getUpdatedAt());
		dto.setStatusMessage(statusMessage);
		return dto;
	}
	
	
	
	public static CreateDepartmentResponseDTO createResponse(Department department) {
		CreateDepartmentResponseDTO dto = new CreateDepartmentResponseDTO();
		
		dto.setID(department.getDepartmentId());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setStatus(department.getStatus());
		dto.setCreatedAt(department.getCreatedAt());
		dto.setStatusMessage("Department " + department.getName() + " is created");
		return dto;
	}
	
	public static Page<DepartmentResponseDTO> getAllResponse(Page<Department> department) {
		return department.map(dept -> DepartmentMapper.toResponse(dept, "All Department"));
	}
	
	public static Page<DepartmentResponseDTO> activeDepartmentResponse(Page<Department> department, String statusMessage) {
			return department.map(dept -> DepartmentMapper.toResponse(dept, statusMessage));
	}
		
	
	

}
