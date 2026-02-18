package com.prince.ems.dto.employee;

import java.math.BigDecimal;

import com.prince.ems.entity.Department;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEmployeeRequestDTO {
	
	@NotBlank(message = "Name must not be Empty")
	private String name;
	
	@NotBlank(message = "Email must not be Empty")
	@Email
	private String email;
	

    @NotNull(message = "Salary must not be blank")
    private BigDecimal salary;
		

    @NotNull(message = "Department ID must not be blank")
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
