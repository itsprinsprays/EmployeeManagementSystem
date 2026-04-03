package com.prince.ems.dto.employee;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class UpdateEmployeeRequestDTO {
	
	@Size(min = 3, message = "Name must be at least 3 Characters long")
	@Size(max = 30, message = "Name must not exceed 30 characters")
	private String name;
	
	@Email(message = "Invalid Email Format use '@'")
	private String email;
	
	private BigDecimal salary;
	
	private Long departmentId;
	
	public UpdateEmployeeRequestDTO() { }
	
	public String getName() { return name; }
	public String getEmail() { return email; }
	public BigDecimal getSalary( ) { return salary; }
	public Long getDepartmentId() { return departmentId; }

}
