package com.prince.ems.dto;

import com.prince.ems.entity.Status;


public class PartialUpdateRequestDTO {
	
	private String name;
	
	private String description;
	private Status status;
	
	public PartialUpdateRequestDTO() { }
	
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
