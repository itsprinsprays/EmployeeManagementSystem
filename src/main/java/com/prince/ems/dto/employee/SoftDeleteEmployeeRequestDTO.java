package com.prince.ems.dto.employee;

import com.prince.ems.entity.Status;

public class SoftDeleteEmployeeRequestDTO {
	
	private Status status;
	
	public SoftDeleteEmployeeRequestDTO() {}

	public Status getStatus() { return status; }
	
	public void setStatus(Status status) { this.status = status; }
	
}
