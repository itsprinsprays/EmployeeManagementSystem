package com.prince.ems.dto.department;

import com.prince.ems.entity.Status;

import jakarta.validation.constraints.NotBlank;

public class SoftDeleteDepartmentDTO {
	
	@NotBlank()
	Status status;
	
	public SoftDeleteDepartmentDTO() {}
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) { this.status = status; }

}
