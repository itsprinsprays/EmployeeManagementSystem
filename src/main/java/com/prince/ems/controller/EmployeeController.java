package com.prince.ems.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.service.EmployeeService;

import jakarta.validation.Valid;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetEmployeeResponseDTO;
import com.prince.ems.dto.employee.SoftDeleteEmployeeResponseDTO;
import com.prince.ems.dto.employee.UpdateEmployeeRequestDTO;
import com.prince.ems.dto.employee.UpdateEmployeeResponseDTO;
import com.prince.ems.entity.Status;

@RequestMapping("/employee")
@RestController
@Validated
public class EmployeeController {
	
	private final EmployeeService serv;
	
	public EmployeeController(EmployeeService serv) {
		this.serv = serv;
	}
	
	@PostMapping
	public ResponseEntity<CreateEmployeeResponseDTO> createEmployee(@Valid @RequestBody CreateEmployeeRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serv.createEmployee(dto));
	}
	
//	@GetMapping
//	public ResponseEntity<Page<GetEmployeeResponseDTO>> getAllEmployee(
//			@RequestParam(defaultValue = "0") int page,
//		    @RequestParam(defaultValue = "5") int size) {
//		
//		Pageable pageable = PageRequest.of(page, size,Sort.by("name").ascending()
//				.and(Sort.by("id").ascending()));
//		return ResponseEntity.ok().body(serv.getAllEmployee(pageable));
//	}
//	
	@GetMapping("/status")
	public ResponseEntity<Page<GetEmployeeResponseDTO>> getAllActiveEmployee(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size,Sort.by("name").ascending()
				.and(Sort.by("id").ascending()));
		return ResponseEntity.ok().body(serv.getAllActiveEmployee(pageable));
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<GetEmployeeResponseDTO> getEmployeeById(@PathVariable Long Id) {
		return ResponseEntity.ok().body(serv.getEmployeeById(Id));
	}
	
	@PatchMapping("/{Id}")
	public ResponseEntity<UpdateEmployeeResponseDTO> updateEmployee(
				@PathVariable Long Id,
				@RequestBody UpdateEmployeeRequestDTO dto) {
		
		return ResponseEntity.ok().body(serv.partialUpdate(dto, Id));
		
	}
	
	@PatchMapping("/{Id}/status/{status}")
	public ResponseEntity<SoftDeleteEmployeeResponseDTO> updateStatus(@PathVariable Long Id, @PathVariable Status status) {
		return ResponseEntity.ok().body(serv.updateStatus(Id, status));
	}
	
	@GetMapping
	public ResponseEntity<Page<GetEmployeeResponseDTO>> getAllEmployeeSpecification(
				@RequestParam(required = false) String name,
				@RequestParam(required = false) Status status,
				@RequestParam(required = false) Long Id,
				@RequestParam(required = false) Double minSalary,
				@RequestParam(required = false) Double maxSalary,
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending().and(Sort.by("id").ascending()));
		return ResponseEntity.ok().body(serv.getAllEmployeeSpecification(name, status, Id, minSalary, maxSalary, pageable));
		
	}
	
	

}
