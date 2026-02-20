package com.prince.ems.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.service.EmployeeService;

import jakarta.validation.Valid;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;
import com.prince.ems.dto.employee.GetEmployeeResponseDTO;

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
	
	@GetMapping
	public ResponseEntity<List<GetEmployeeResponseDTO>> getAllEmployee() {
		return ResponseEntity.ok().body(serv.getAllEmployee());
	}
	
	@GetMapping("/status")
	public ResponseEntity<Page<GetEmployeeResponseDTO>> getAllActiveEmployee(@RequestParam(defaultValue = "0") int page,
															@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok().body(serv.getAllActiveEmployee(pageable));
		
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<GetEmployeeResponseDTO> getEmployeeById(@PathVariable Long Id) {
		return ResponseEntity.ok().body(serv.getEmployeeById(Id));
	}
	
	

}
