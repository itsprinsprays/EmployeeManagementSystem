package com.prince.ems.controller;

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

import com.prince.ems.dto.department.CreateDepartmentResponseDTO;
import com.prince.ems.dto.department.DepartmentRequestDTO;
import com.prince.ems.dto.department.DepartmentResponseDTO;
import com.prince.ems.dto.department.PartialUpdateRequestDTO;
import com.prince.ems.entity.Status;
import com.prince.ems.service.DepartmentService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartment(@RequestParam(defaultValue = "0") int page,
																		@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok().body(serv.getAllDepartment(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
		DepartmentResponseDTO response = serv.getDepartmentById(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/active")
	public ResponseEntity<Page<DepartmentResponseDTO>> getActiveDepartment(@RequestParam(defaultValue = "0") int page,
																	 @RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<DepartmentResponseDTO> response = serv.getActiveDepartment(pageable);
		return ResponseEntity.ok(response);
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<DepartmentResponseDTO> partialUpdate(@Valid @RequestBody PartialUpdateRequestDTO dto, @PathVariable Long id) {
		DepartmentResponseDTO response = serv.partialUpdateDepartmentById(dto, id);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/{status}")
	public ResponseEntity<DepartmentResponseDTO> softDelete(@PathVariable Long id, @PathVariable Status status) {
		DepartmentResponseDTO response = serv.statusActivation(id, status);
		return ResponseEntity.ok(response);
	}
}
