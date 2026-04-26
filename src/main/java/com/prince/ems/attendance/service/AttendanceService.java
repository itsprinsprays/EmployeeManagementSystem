package com.prince.ems.attendance.service;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.prince.ems.attendance.dto.TimeInRequestDTO;
import com.prince.ems.attendance.dto.TimeInResponseDTO;
import com.prince.ems.attendance.mapper.AttendanceMapper;
import com.prince.ems.attendance.repository.AttendanceRepository;
import com.prince.ems.entity.Attendance;
import com.prince.ems.entity.AttendanceStatus;
import com.prince.ems.entity.Employee;
import com.prince.ems.exception.ResourceNotFoundException;
import com.prince.ems.repository.EmployeeRepository;

@Service
public class AttendanceService {
	
	private final AttendanceRepository arepo;
	private final EmployeeRepository erepo;
	
	public AttendanceService(AttendanceRepository arepo, EmployeeRepository erepo) {
		this.arepo = arepo;
		this.erepo = erepo;
	}
	
	public TimeInResponseDTO timeIn(TimeInRequestDTO dto) {
		Employee employee = erepo.findById(dto.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID '" + dto.getEmployeeId()  + "' does not exist"));
		
		Attendance attendance = new Attendance();
		attendance.setEmployee(employee);
		
		LocalTime now = LocalTime.now();
		attendance.setTimeIn(now);

		LocalTime scheduledStart = LocalTime.of(22, 0);
		LocalTime scheduledEnd = LocalTime.of(23, 59);
		
		if(attendance.getTimeOut() != null) {
		Duration duration = Duration.between(attendance.getTimeIn(), attendance.getTimeOut());
		Long hours = duration.toHours();
		attendance.setTotalHours(hours);
		}
		
		if (now.isAfter(scheduledEnd)) {
		    attendance.setStatus(AttendanceStatus.ABSENT);
		} else if (now.isAfter(scheduledStart)) {
		    attendance.setStatus(AttendanceStatus.LATE);
		} else {
		    attendance.setStatus(AttendanceStatus.PRESENT);
		}
		
		arepo.save(attendance);
		
		return AttendanceMapper.timeInResponse(attendance);
		
		
		
		
	}

}
