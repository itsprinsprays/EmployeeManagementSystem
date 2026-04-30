package com.prince.ems.attendance.dto;


import jakarta.validation.constraints.NotNull;

public class TimeInRequestDTO {
	
	@NotNull(message = "ID is required")
	private Long employeeId;
	
	
	public TimeInRequestDTO() { }
	
	public Long getEmployeeId() { return employeeId; }
	
	public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

}
