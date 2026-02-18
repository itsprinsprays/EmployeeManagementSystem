package com.prince.ems.dto.department;

import com.prince.ems.entity.Status;


public class PartialUpdateRequestDTO {
	
	private String name;
	
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
