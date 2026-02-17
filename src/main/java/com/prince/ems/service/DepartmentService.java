package com.prince.ems.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.CreateDepartmentResponseDTO;
import com.prince.ems.dto.DepartmentRequestDTO;
import com.prince.ems.dto.DepartmentResponseDTO;
import com.prince.ems.dto.PartialUpdateRequestDTO;
import com.prince.ems.entity.Department;
import com.prince.ems.entity.Status;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.exception.ResourceNotFoundException;
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
	public CreateDepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {    //Create Department
		if(repo.existsByName(dto.getName()))
			throw new DuplicateResponseException(dto.getName() + " Department is already existing");
		
		
		Department department = new Department();
		department.setName(dto.getName());
		department.setDescription(dto.getDescription());
		
		repo.save(department);
		
		return DepartmentMapper.createResponse(department);

	}
	 
	public List<DepartmentResponseDTO> getAllDepartment() {                        //Get All Department active or inactive
			List<Department> dto = repo.findAll();  
			return DepartmentMapper.getAllResponse(dto);
	}
	
	public Page<DepartmentResponseDTO> getActiveDepartment(Pageable pageable) {       //Get All Active Departments
			Page<Department> page = repo.findByStatus(Status.ACTIVE, pageable);
			return DepartmentMapper.activeDepartmentResponse(page, "ACTIVE DEPARTMENTS");
	}
	
	@Transactional
	public DepartmentResponseDTO getDepartmentById(Long id) {                       //Get Department using ID
		Department department = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " Department not found"));
			
		return DepartmentMapper.toResponse(department, "COMPANY DEPARTMENT");
			
	}
	
	@Transactional
	public DepartmentResponseDTO partialUpdateDepartmentById(PartialUpdateRequestDTO dto, Long id) {       //Partial Update
		Department department = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " Department not found"));
		
		if(dto.getName() != null) department.setName(dto.getName());
		if(dto.getDescription() != null) department.setDescription(dto.getDescription());
		
		repo.save(department);
		return DepartmentMapper.toResponse(department, "UPDATED");
	}
	
	@Transactional
	public DepartmentResponseDTO statusActivation(Long id, Status status) {      //Soft delete status and activate status
		Department department = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " Department not found"));
		
		department.setStatus(status);
		
		repo.save(department);
		
		return DepartmentMapper.toResponse(department, "UPDATED");
 
		}

}
