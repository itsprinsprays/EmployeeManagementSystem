package com.prince.ems.attendance.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.attendance.dto.TimeInOutResponseDTO;
import com.prince.ems.entity.Attendance;

@Component
public class AttendanceMapper {
	
	public static TimeInOutResponseDTO timeInOutResponse(Attendance attendance) { 
		
		TimeInOutResponseDTO dto = new TimeInOutResponseDTO();
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
