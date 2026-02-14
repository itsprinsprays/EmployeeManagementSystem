package com.prince.ems.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.dto.CreateDepartmentResponseDTO;
import com.prince.ems.dto.DepartmentRequestDTO;
import com.prince.ems.dto.DepartmentResponseDTO;
import com.prince.ems.service.DepartmentService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/department")
@Validated
public class DepartmentController {

	private final DepartmentService serv;
	
	public DepartmentController(DepartmentService serv) {
		this.serv = serv;
	}
	
	@PostMapping
	public ResponseEntity<CreateDepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO dto) {
		CreateDepartmentResponseDTO response = serv.createDepartment(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<DepartmentResponseDTO>> getALlDepartment() {
			List<DepartmentResponseDTO> response = serv.getAllDepartment();
			return ResponseEntity.ok(response);
	}
}
