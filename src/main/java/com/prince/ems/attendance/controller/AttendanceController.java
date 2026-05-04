package com.prince.ems.attendance.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.attendance.dto.MyRecordResponseDTO;
import com.prince.ems.attendance.dto.TimeInOutResponseDTO;
import com.prince.ems.attendance.service.AttendanceService;
import com.prince.ems.dto.PageResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/attendance")
@Validated
public class AttendanceController {
	
	private final AttendanceService serv;
	
	public AttendanceController(AttendanceService serv) {
		this.serv = serv;
	}
	
	@PostMapping("/timeIn/{Id}")
	public ResponseEntity<TimeInOutResponseDTO> timeIn(@Validated @PathVariable Long Id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serv.timeIn(Id));
	}
	
	@PatchMapping("/timeOut/{Id}")
	public ResponseEntity<TimeInOutResponseDTO> timeOut(@Validated @PathVariable Long Id) {
		return ResponseEntity.ok().body(serv.timeOut(Id));
	}
	
	@GetMapping("/myrecords")
	public ResponseEntity<PageResponseDTO<MyRecordResponseDTO>> myRecord(HttpServletRequest request,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok().body(serv.myRecord(request,pageable));
	}

}
