package com.prince.ems.attendance.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.attendance.dto.TimeInOutResponseDTO;
import com.prince.ems.attendance.dto.TimeInRequestDTO;
import com.prince.ems.attendance.dto.TimeOutRequestDTO;
import com.prince.ems.attendance.mapper.AttendanceMapper;
import com.prince.ems.attendance.repository.AttendanceRepository;
import com.prince.ems.entity.Attendance;
import com.prince.ems.entity.AttendanceStatus;
import com.prince.ems.entity.Employee;
import com.prince.ems.exception.ResourceNotFoundException;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.repository.EmployeeRepository;

@Service
public class AttendanceService {
	
	private final AttendanceRepository arepo;
	private final EmployeeRepository erepo;
	
	public AttendanceService(AttendanceRepository arepo, EmployeeRepository erepo) {
		this.arepo = arepo;
		this.erepo = erepo;
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	public TimeInOutResponseDTO timeIn(TimeInRequestDTO dto) {
		
		Attendance attendance = new Attendance();
		LocalTime scheduledStart = LocalTime.of(22, 0);
		LocalTime scheduledEnd = LocalTime.of(23, 59);
		LocalTime now = LocalTime.now();
		LocalDate dateNow = LocalDate.now();
		attendance.setDate(dateNow);

		
		if(arepo.existsByDateAndEmployeeId(dateNow, dto.getEmployeeId()))
			throw new DuplicateResponseException("Today is : " + dateNow + ", no Duplication of attendance");
			
		Employee employee = erepo.findById(dto.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID '" + dto.getEmployeeId()  + "' does not exist"));
	
		
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
	public TimeInOutResponseDTO timeOut(TimeOutRequestDTO dto) {
		
		LocalTime now = LocalTime.now();
		
		Attendance attendance = arepo.findByEmployeeId(dto.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID '" + dto.getEmployeeId()  + "' does not exist"));
		
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
	
	

}
