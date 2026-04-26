package com.prince.ems.attendance.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.attendance.dto.TimeInResponseDTO;
import com.prince.ems.entity.Attendance;

@Component
public class AttendanceMapper {
	
	public static TimeInResponseDTO timeInResponse(Attendance attendance) { 
		
		TimeInResponseDTO dto = new TimeInResponseDTO();
		dto.setId(attendance.getId());
		dto.setDate(attendance.getDate());
		dto.setEmployeeName(attendance.getEmployee().getName());
		dto.setTimeIn(attendance.getTimeIn());
		dto.setTimeOut(attendance.getTimeOut());
		dto.setStatus(attendance.getStatus());
		dto.setTotalHours(attendance.getTotalHours());
		
		return dto;

		
	}

}
