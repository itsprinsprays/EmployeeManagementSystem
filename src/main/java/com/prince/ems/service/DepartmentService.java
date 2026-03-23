package com.prince.ems.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.department.CreateDepartmentResponseDTO;
import com.prince.ems.dto.department.DepartmentRequestDTO;
import com.prince.ems.dto.department.DepartmentResponseDTO;
import com.prince.ems.dto.department.PartialUpdateRequestDTO;
import com.prince.ems.dto.department.SoftDeleteDepartmentDTO;
import com.prince.ems.entity.Department;
import com.prince.ems.entity.Status;
import com.prince.ems.exception.BadRequestException;
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
	
	@PreAuthorize("hasRole('ADMIN')")
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
	 
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public Page<DepartmentResponseDTO> getAllDepartment(Pageable pageable) {
		Page<Department> page = repo.findAll(pageable);
		return DepartmentMapper.getAllResponse(page);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public Page<DepartmentResponseDTO> getDepartmentStatus(Status status, Pageable pageable) {       //Get All Active Departments
			Page<Department> page = repo.findByStatus(status, pageable);
			return DepartmentMapper.activeDepartmentResponse(page, "ACTIVE DEPARTMENTS");
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	@Transactional
	public DepartmentResponseDTO getDepartmentById(Long Id) {                     		  //Get Department using ID
		Department department = repo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException(Id + " Department not found"));
			
		return DepartmentMapper.toResponse(department, "COMPANY DEPARTMENT");
			
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public DepartmentResponseDTO partialUpdateDepartmentById(PartialUpdateRequestDTO dto, Long id) {       //Partial Update
		Department department = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " Department not found"));
		
		if(repo.existsByName(dto.getName()))
			throw new DuplicateResponseException(dto.getName() + " Department is already existing");
		
		if((dto.getName() == null || dto.getName().isBlank()) && (dto.getDescription() == null || dto.getDescription().isBlank()))
			throw new BadRequestException("At least one field must be provided for update");
		
		if(dto.getName() != null) department.setName(dto.getName());
		if(dto.getDescription() != null) department.setDescription(dto.getDescription());
		
		repo.save(department);
		return DepartmentMapper.toResponse(department, "UPDATED");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public DepartmentResponseDTO statusActivation(Long id, SoftDeleteDepartmentDTO dto) {     				 //Soft delete status and activate status
		Department department = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id + " Department not found"));
		
		if(dto.getStatus() == null || dto.getStatus().toString().isBlank()) 
			throw new BadRequestException("At least one field must be provided for update");
		
		department.setStatus(dto.getStatus());
		
		repo.save(department);
		
		return DepartmentMapper.toResponse(department, "UPDATED");
 
		}

}
