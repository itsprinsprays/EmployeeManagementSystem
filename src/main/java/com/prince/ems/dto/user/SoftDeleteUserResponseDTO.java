package com.prince.ems.dto.user;

public class SoftDeleteUserResponseDTO {
	
	private Long employeeId;
	private String message;
	
	public SoftDeleteUserResponseDTO() { }
	
	public Long employeeId() { return employeeId; }
	public String getMessage() { return message; }
	
	public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
	public void setMessage(String message) { this.message = message; }
}
