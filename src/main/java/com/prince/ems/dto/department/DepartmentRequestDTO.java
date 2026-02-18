package com.prince.ems.dto.department;


import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {
	
	@NotBlank(message = "Name must not be Empty")
	private String name;
	
	@NotBlank(message = "Description must not be Empty")
	private String description;
	
	
	public DepartmentRequestDTO() { }
	
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
