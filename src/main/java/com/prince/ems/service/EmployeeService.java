package com.prince.ems.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetAllEmployeeResponseDTO;
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
	
	public Page<GetAllEmployeeResponseDTO> getActive(Pageable page) {
		Page<Employee> employee = erepo.findByStatus(Status.ACTIVE, page);
		return EmployeeMapper.getActiveResponse(employee);		
	}
	 

}
