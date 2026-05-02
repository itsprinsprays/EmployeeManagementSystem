package com.prince.ems.attendance.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.attendance.dto.MyRecordResponseDTO;
import com.prince.ems.attendance.dto.TimeInOutResponseDTO;
import com.prince.ems.attendance.mapper.AttendanceMapper;
import com.prince.ems.attendance.repository.AttendanceRepository;
import com.prince.ems.entity.Attendance;
import com.prince.ems.entity.AttendanceStatus;
import com.prince.ems.entity.Employee;
import com.prince.ems.exception.ResourceNotFoundException;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.repository.EmployeeRepository;
import com.prince.ems.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AttendanceService {
	
	private final AttendanceRepository arepo;
	private final EmployeeRepository erepo;
	private final JwtUtil util;
	
	public AttendanceService(AttendanceRepository arepo, EmployeeRepository erepo, JwtUtil util) {
		this.arepo = arepo;
		this.erepo = erepo;
		this.util = util;
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	public TimeInOutResponseDTO timeIn(Long Id) {
		
		Attendance attendance = new Attendance();
		LocalTime scheduledStart = LocalTime.of(22, 0);
		LocalTime scheduledEnd = LocalTime.of(23, 59);
		LocalTime now = LocalTime.now();
		LocalDate dateNow = LocalDate.now();
		attendance.setDate(dateNow);

		
		if(arepo.existsByDateAndEmployeeId(dateNow, Id))
			throw new DuplicateResponseException(dateNow + ": no Duplication of attendance");
			
		Employee employee = erepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID '" + Id  + "' does not exist"));
	
		
		attendance.setTimeIn(now);
		attendance.setEmployee(employee);
		
		if (now.isAfter(scheduledEnd)) {
		    attendance.setStatus(AttendanceStatus.ABSENT);
		} else if (now.isAfter(scheduledStart)) {
		    attendance.setStatus(AttendanceStatus.LATE);
		} else {
		    attendance.setStatus(AttendanceStatus.PRESENT);
		}
		
		arepo.save(attendance);
		
		return AttendanceMapper.timeInOutResponse(attendance);
		
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	public TimeInOutResponseDTO timeOut(Long Id) {
		
		LocalTime now = LocalTime.now();
		
		Attendance attendance = arepo.findByDateAndEmployeeId(LocalDate.now(), Id)
		        .orElseThrow(() -> new ResourceNotFoundException("No attendance record for employee ID '" + Id + "' today"));
		
		if(attendance.getTimeOut() != null) 
			throw new DuplicateResponseException("No Duplication of TimeOut");

		if(attendance.getStatus() == AttendanceStatus.ABSENT)
			throw new BadRequestException("Employee should at least Time In Earlier");
		
		attendance.setTimeOut(now);
		
		Duration duration = Duration.between(attendance.getTimeIn(), attendance.getTimeOut());
		Long hours = duration.toHours();
		attendance.setTotalHours(hours);
		
		arepo.save(attendance);
		
		return AttendanceMapper.timeInOutResponse(attendance);
	}
	
	public MyRecordResponseDTO myRecord(HttpServletRequest request) {
		
		String header = request.getHeader("Authorization");
				
		if(header != null && !header.startsWith("Bearer ")) 
	        throw new BadRequestException("Missing or invalid Authorization header");
		
		String email = header.substring(7);
		String username = util.extractUsername(email);
		
		
		Employee employee = erepo.findByEmail(username)
				.orElseThrow(() -> new BadRequestException("Error"));
		
		
		Attendance attendance = arepo.findByEmployeeId(employee.getId()) 
				.orElseThrow(() -> new BadRequestException("Error"));
		
		
		return AttendanceMapper.myRecordResponse(attendance);
		
	}
	
	
	
	

}
