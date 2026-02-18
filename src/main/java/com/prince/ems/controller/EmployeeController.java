package com.prince.ems.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.dto.employee.CreateEmployeeRequestDTO;
import com.prince.ems.service.EmployeeService;

import jakarta.validation.Valid;

import com.prince.ems.dto.employee.CreateEmployeeResponseDTO;

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

}
