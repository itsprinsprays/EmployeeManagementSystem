package com.prince.ems.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prince.ems.dto.CreateDepartmentResponseDTO;
import com.prince.ems.dto.DepartmentResponseDTO;
import com.prince.ems.entity.Department;



@Component
public class DepartmentMapper {
	
	public static DepartmentResponseDTO toResponse(Department department) {
		DepartmentResponseDTO dto = new DepartmentResponseDTO();
		
		dto.setID(department.getID());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setStatus(department.getStatus());
		dto.setCreatedAt(department.getCreatedAt());
		dto.setUpdatedAt(department.getUpdatedAt());
		dto.setStatusMessage("Department " + department.getName() + " is created");
		return dto;
	}
	
	public static CreateDepartmentResponseDTO createResponse(Department department) {
		CreateDepartmentResponseDTO dto = new CreateDepartmentResponseDTO();
		
		dto.setID(department.getID());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setStatus(department.getStatus());
		dto.setCreatedAt(department.getCreatedAt());
		dto.setStatusMessage("Department " + department.getName() + " is created");
		return dto;
	}
	
	public static List<DepartmentResponseDTO> getAlLResponse(List<Department> department) {
		return department.stream()
						.map(e -> DepartmentMapper.toResponse(e))
						.toList();
	}

}
