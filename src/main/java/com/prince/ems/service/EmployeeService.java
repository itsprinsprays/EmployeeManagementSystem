package com.prince.ems.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetEmployeeResponseDTO;
import com.prince.ems.dto.employee.UpdateEmployeeRequestDTO;
import com.prince.ems.dto.employee.UpdateEmployeeResponseDTO;
import com.prince.ems.entity.Department;
import com.prince.ems.entity.Employee;
import com.prince.ems.entity.Status;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.exception.ResourceNotFoundException;
import com.prince.ems.mapper.EmployeeMapper;
import com.prince.ems.repository.DepartmentRepository;
import com.prince.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private final EmployeeRepository erepo;
	private final DepartmentRepository drepo;
	
	public EmployeeService(EmployeeRepository erepo, DepartmentRepository drepo) {
		this.erepo = erepo;
		this.drepo = drepo;
	}
	
	//Create
	@Transactional
	public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO dto) {
		if(erepo.existsByEmail(dto.getEmail()))
			throw new DuplicateResponseException("Email '" + dto.getEmail() + "' is existing");
		
		Department department = drepo.findById(dto.getDepartmentID())
				.orElseThrow(() -> new ResourceNotFoundException("Department ID '" + dto.getDepartmentID() + "' not Found "));
		
		if(dto.getSalary().compareTo(BigDecimal.ZERO) <= 0) 
			throw new BadRequestException("Salary must be greater than zero");
		
		Employee employee = new Employee();
		employee.setName(dto.getName());
		employee.setEmail(dto.getEmail());
		employee.setSalary(dto.getSalary());
		employee.setDepartment(department);
		
		erepo.save(employee);
		
		return EmployeeMapper.createResponse(employee);
		
	}
	
	//Get Active Employee
	public Page<GetEmployeeResponseDTO> getAllActiveEmployee(Pageable page) {
		Page<Employee> employee = erepo.findByStatus(Status.ACTIVE, page);
		return EmployeeMapper.getActiveResponse(employee);		
	}
	
	//Get Employee by ID
	public GetEmployeeResponseDTO getEmployeeById(Long Id) {
		Employee employee = erepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee ID '" + Id + "' not Found "));
		
		return EmployeeMapper.getEmployeeById(employee);
	}
	
	//Get All employee
	public Page<GetEmployeeResponseDTO> getAllEmployee(Pageable pageable) {
		Page<Employee> employee = erepo.findAll(pageable);
		return EmployeeMapper.getAllEmployeeResponse(employee);
	}
	
	@Transactional
	public UpdateEmployeeResponseDTO partialUpdate(UpdateEmployeeRequestDTO dto, Long Id) {
			Employee employee = erepo.findById(Id).orElseThrow(() -> 	
			new ResourceNotFoundException("Employee ID '" + Id + "' not Found "));
			
			if(dto.getEmail() != null && erepo.existsByEmailAndIdNot(dto.getEmail(), Id))
			throw new ResourceNotFoundException("Email '" + dto.getEmail() + "' is existing");
			
			if(dto.getSalary() != null && dto.getSalary().compareTo(BigDecimal.ZERO) <= 0) 
			throw new BadRequestException("Salary must be greater than zero");
	
		
		if(dto.getName() != null) employee.setName(dto.getName());
		if(dto.getEmail() != null) employee.setEmail(dto.getEmail());
		if(dto.getSalary() != null) employee.setSalary(dto.getSalary());
		
		erepo.save(employee);
		
		return EmployeeMapper.updateResponse(employee);
		
	}

	
	
	 

}
