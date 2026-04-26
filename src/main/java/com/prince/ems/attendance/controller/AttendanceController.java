package com.prince.ems.attendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.attendance.dto.TimeInRequestDTO;
import com.prince.ems.attendance.dto.TimeInResponseDTO;
import com.prince.ems.attendance.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@Validated
public class AttendanceController {
	
	private final AttendanceService serv;
	
	public AttendanceController(AttendanceService serv) {
		this.serv = serv;
	}
	
	@PostMapping("/timeIn")
	public ResponseEntity<TimeInResponseDTO> timeIn(@RequestBody @Validated TimeInRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serv.timeIn(dto));
	}

}
