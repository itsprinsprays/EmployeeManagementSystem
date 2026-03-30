package com.prince.ems.dto.user;

import com.prince.ems.entity.Status;

import jakarta.validation.constraints.NotBlank;

public class SoftDeleteUserRequestDTO {
	
	@NotBlank()
	private Status status;

	public SoftDeleteUserRequestDTO() { }
	
	public Status getStatus() { return status; }
	
	public void setStatus(Status status) { this.status = status; }
}
