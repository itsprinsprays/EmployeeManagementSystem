package com.prince.ems.dto;

import com.prince.ems.entity.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DepartmentRequestDTO {
	
	@NotBlank(message = "Name must not be Empty")
	private String name;
	
	@NotBlank(message = "Description must not be Empty")
	private String description;
	
	@NotNull(message = "Active or Inactive Only")
	private Status status;
	
	public DepartmentRequestDTO() { }
	
	// Getters
	public String getName() {
	    return name;
	}

	public String getDescription() {
	    return description;
	}

	public Status getStatus() {
	    return status;
	}

	// Setters
	public void setName(String name) {
	    this.name = name;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public void setStatus(Status status) {
	    this.status = status;
	}
	
	

}
