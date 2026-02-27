package com.prince.ems.dto.employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.prince.ems.entity.Department;
import com.prince.ems.entity.Status;


public class CreateEmployeeResponseDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private BigDecimal salary;
	
	private Status status = Status.ACTIVE;
	
	private LocalDate hireDate;
	
	private Long departmentId;
	
	private LocalDateTime createdAt;
	
	
	public CreateEmployeeResponseDTO() { }
	
	public Long getID() { return id; }
	public String getName() { return name; }
	public String getEmail() { return email; }
	public BigDecimal getSalary() { return salary; }
	public Status getStatus() { return status; }
	public LocalDate getHireDate() { return hireDate; }
	public Long getDepartmentId() { return departmentId; } 
	public LocalDateTime getCreatedAt() { return createdAt; }
	
	public void setID(Long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setEmail(String email) { this.email = email; } 
	public void setSalary(BigDecimal salary) { this.salary = salary; }
	public void setStatus(Status status) { this.status = status; }
	public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
	public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	

}
