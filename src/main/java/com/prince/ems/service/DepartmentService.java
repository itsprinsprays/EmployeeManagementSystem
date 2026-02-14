package com.prince.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prince.ems.dto.CreateDepartmentResponseDTO;
import com.prince.ems.dto.DepartmentRequestDTO;
import com.prince.ems.dto.DepartmentResponseDTO;
import com.prince.ems.entity.Department;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.mapper.DepartmentMapper;
import com.prince.ems.repository.DepartmentRepository;

import jakarta.transaction.Transactional;


@Service
public class DepartmentService {
	
	private final DepartmentRepository repo;
	
	public DepartmentService(DepartmentRepository repo) {
		this.repo = repo;
	}
	
	@Transactional
	public CreateDepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
		if(repo.existsByName(dto.getName()))
			throw new DuplicateResponseException(dto.getName() + " is already existing");
		
		
		Department department = new Department();
		department.setName(dto.getName());
		department.setDescription(dto.getDescription());
		department.setStatus(dto.getStatus());
		
		repo.save(department);
		
		return DepartmentMapper.createResponse(department);

	}
	
	public List<DepartmentResponseDTO> getAllDepartment() {
			List<Department> dto = repo.findAll();  
			return DepartmentMapper.getAlLResponse(dto);
	}

}
