package com.prince.ems.dto.user;

import com.prince.ems.entity.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistrationUserRequestDTO {
	
	@NotBlank(message = "Username must not be blank")
	private String username;
	
	@NotNull(message = "Password must not be null")
	private String password;
	
	@NotNull(message = "Role must not be null")
	private Role role;
	
	@NotNull(message = "Employee ID must not be null")
	private Long employeeID;
	
	public RegistrationUserRequestDTO() { }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

	
	

}
