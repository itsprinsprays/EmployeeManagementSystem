package com.prince.ems.dto.employee;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;



public class UpdateEmployeeRequestDTO {
	
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
