package com.prince.ems.dto.department;

import com.prince.ems.entity.Status;

import jakarta.validation.constraints.Size;


public class PartialUpdateRequestDTO {
	
	@Size(min = 5, max = 30, message = "Name should be at least 5 and maximum of 30 characters")
	private String name;
	
	@Size(min = 10, max = 50, message = "Description should be at least 10 and maximum of 50 characters")
	private String description;
	
	public PartialUpdateRequestDTO() { }
	
	// Getters
	public String getName() {
	    return name;
	}

	public String getDescription() {
	    return description;
	}


	// Setters
	public void setName(String name) {
	    this.name = name;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

}
