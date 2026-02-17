package com.prince.ems.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.prince.ems.dto.CreateDepartmentResponseDTO;
import com.prince.ems.dto.DepartmentResponseDTO;
import com.prince.ems.dto.PartialUpdateRequestDTO;
import com.prince.ems.entity.Department;



@Component
public class DepartmentMapper {
	
	public static DepartmentResponseDTO toResponse(Department department, String statusMessage) {
		DepartmentResponseDTO dto = new DepartmentResponseDTO();
		
		dto.setID(department.getID());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setStatus(department.getStatus());
		dto.setCreatedAt(department.getCreatedAt());
		dto.setUpdatedAt(dto.getUpdatedAt());
		dto.setStatusMessage("statusMessage");
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
	
	public static List<DepartmentResponseDTO> getAllResponse(List<Department> department) {
		return department.stream()
						.map(e -> DepartmentMapper.toResponse(e, "COMPANY DEPARTMENT"))
						.toList();
	}
	
	public static Page<DepartmentResponseDTO> activeDepartmentResponse(Page<Department> department, String statusMessage) {
			return department.map(dept -> DepartmentMapper.toResponse(dept, statusMessage));

	}
	
	
	

}
