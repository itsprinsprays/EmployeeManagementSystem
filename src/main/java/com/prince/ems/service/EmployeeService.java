package com.prince.ems.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.dto.PageResponseDTO;
import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetEmployeeResponseDTO;
import com.prince.ems.dto.employee.SoftDeleteEmployeeRequestDTO;
import com.prince.ems.dto.employee.SoftDeleteEmployeeResponseDTO;
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
import com.prince.ems.specification.EmployeeSpecification;

@Service
public class EmployeeService {
	
	private final EmployeeRepository erepo;
	private final DepartmentRepository drepo;
	
	public EmployeeService(EmployeeRepository erepo, DepartmentRepository drepo) {
		this.erepo = erepo;
		this.drepo = drepo;
	}
	
	//Create
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "employeesSpec", allEntries = true),
		@CacheEvict(value = "employeesStat", allEntries = true)
	})
	public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO dto) {
 			
		if(erepo.existsByEmail(dto.getEmail()))
			throw new DuplicateResponseException("Email '" + dto.getEmail() + "' is already in use");
		
		Department department = drepo.findById(dto.getDepartmentID())
				.orElseThrow(() -> new ResourceNotFoundException("Department with ID '" + dto.getDepartmentID() + "' does not exist"));
		
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
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	@Transactional(readOnly = true)
	@Cacheable(value = "employeesStat", key = "#status + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
	public PageResponseDTO<GetEmployeeResponseDTO> getEmployeeStatus(Status status, Pageable pageable) {
		System.out.print("Fetching from db..");
		Page<Employee> employee = erepo.findByStatus(status, pageable);
		return EmployeeMapper.toPageResponseActive(employee);		
	}
	
	//Get Employee by ID
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	@Transactional(readOnly = true)
	@Cacheable(value = "employees", key = "#Id")
	public GetEmployeeResponseDTO getEmployeeById(Long Id) {
		System.out.print("Fetching from db..");
		Employee employee = erepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee ID '" + Id + "' does not exist"));
		
		return EmployeeMapper.getEmployeeById(employee);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	@Transactional(readOnly = true)
	@Cacheable(
		    value = "employeesSpec",
		    key = "#name + '-' + #status + '-' + #departmentId + '-' + #minSalary + '-' + #maxSalary + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()"
		)
	public PageResponseDTO<GetEmployeeResponseDTO> getAllEmployeeSpecifications(
			String name, 
			Status status, 
			Long departmentId,
			Double minSalary,
			Double maxSalary,
			Pageable pageable	) {
		
		System.out.print("Fetching from db..");
		Specification<Employee> spec = Specification.where(null);
		
		if(name != null)  spec = spec.and(EmployeeSpecification.hasName(name));
		if(status != null) spec = spec.and(EmployeeSpecification.hasStatus(status));
		if(departmentId != null) spec = spec.and(EmployeeSpecification.hasDepartment(departmentId));
		if(minSalary != null && maxSalary != null) spec = spec.and(EmployeeSpecification.betweenSalary(minSalary, maxSalary));
		
		Page<Employee> employee = erepo.findAll(spec, pageable);
		
		return EmployeeMapper.toPageResponseSpecifications(employee);
		
	}
	
	//Get All employee
//	@PreAuthorize("hasAnyRole('ADMIN','HR')")
//	public Page<GetEmployeeResponseDTO> getAllEmployee(Pageable pageable) {
//		Page<Employee> employee = erepo.findAll(pageable);
//		return EmployeeMapper.getAllEmployeeResponse(employee);
//	}
	
	
	
	//Partial Update
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "employees", key = "#Id"),
		@CacheEvict(value = "employeesSpec", allEntries = true),
		@CacheEvict(value = "employeesStat", allEntries = true)
	})
	public UpdateEmployeeResponseDTO partialUpdate(UpdateEmployeeRequestDTO dto, Long Id) {
			Employee employee = erepo.findById(Id).orElseThrow(() -> 	
			new ResourceNotFoundException("Employee with ID '" + Id + "' does not exist"));
			
			if (dto.getDepartmentId() != null) {
		    Department dept = drepo.findById(dto.getDepartmentId())
			        .orElseThrow(() -> new ResourceNotFoundException("Department with ID '" + dto.getDepartmentId() + "' does not exist"));
			        
					employee.setDepartment(dept);
			}
			
			if(dto.getName() == null && dto.getEmail() == null && dto.getDepartmentId() == null && dto.getSalary() == null) 
				throw new BadRequestException("At least one field must be provided for update");
			
			if(dto.getEmail() != null && erepo.existsByEmailAndIdNot(dto.getEmail(), Id))
			throw new DuplicateResponseException("Email '" + dto.getEmail() + "' is existing");
			
			if(dto.getSalary() != null && dto.getSalary().compareTo(BigDecimal.ZERO) <= 0) 
			throw new BadRequestException("Salary must be greater than zero");
			
			if(dto.getName() != null) employee.setName(dto.getName());
			if(dto.getEmail() != null) employee.setEmail(dto.getEmail());
			if(dto.getSalary() != null) employee.setSalary(dto.getSalary());
	
			
			erepo.save(employee);
			
			return EmployeeMapper.updateResponse(employee);
	}

	//Soft Delete
//	@Caching(evict = {
//			@CacheEvict(value = "employeesSpec", allEntries = true)       //hybrid caching
//			},
//			put = {
//			@CachePut(value = "employees", key = "#Id")	
//			})
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "employees", key = "#Id"),
			@CacheEvict(value = "employeesStat", allEntries = true),
			@CacheEvict(value = "employeesSpec", allEntries = true)
		})
	public SoftDeleteEmployeeResponseDTO updateStatus(Long Id, SoftDeleteEmployeeRequestDTO dto) {
	
		Employee employee = erepo.findById(Id).orElseThrow(() ->
				new ResourceNotFoundException("Employee with ID '" + Id + "' does not exist"));
		
		
		if(dto.getStatus() == null || dto.getStatus().toString().isBlank()) 
			throw new BadRequestException("At least one field must be provided for update");
		
		employee.setStatus(dto.getStatus());
		
		erepo.save(employee);
		
		return EmployeeMapper.statusUpdate(employee);	
	}
	
	 

}
