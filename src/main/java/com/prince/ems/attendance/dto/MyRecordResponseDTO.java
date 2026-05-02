package com.prince.ems.attendance.dto;

import java.time.LocalDate;

import com.prince.ems.entity.AttendanceStatus;

public class MyRecordResponseDTO {
	
	private LocalDate date;
	private AttendanceStatus status;
	
	public MyRecordResponseDTO() { }
	
	public LocalDate getDate() { return date; }
	public AttendanceStatus getStatus() { return status; }
	
	public void setDate(LocalDate date) { this.date = date; }
	public void setStatus(AttendanceStatus status) { this.status = status; }
	

}
