package com.prince.ems.dto.employee;

import java.math.BigDecimal;

import com.prince.ems.entity.Department;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateEmployeeRequestDTO {
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, message = "Name must be at least 3 Characters long")
	@Size(max = 30, message = "Name must not exceed 30 characters")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email format is invalid")
	private String email;
	

    @NotNull(message = "Salary is required")
    private BigDecimal salary;
		

    @NotNull(message = "Department ID is required")
    private Long departmentID;
	
	public CreateEmployeeRequestDTO() {}
	
	// --- Getters ---
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Long getDepartmentID() {
        return departmentID;
    }

    // --- Setters ---
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

	
	
	
	

}
