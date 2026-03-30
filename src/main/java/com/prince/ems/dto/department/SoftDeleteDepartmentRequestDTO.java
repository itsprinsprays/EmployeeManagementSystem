package com.prince.ems.dto.department;

import com.prince.ems.entity.Status;

import jakarta.validation.constraints.NotBlank;

public class SoftDeleteDepartmentRequestDTO {
	
	@NotBlank()
	Status status;
	
	public SoftDeleteDepartmentRequestDTO() {}
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) { this.status = status; }

}
