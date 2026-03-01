package com.prince.ems.dto.user;

import com.prince.ems.entity.Role;

public class RegistrationUserResponseDTO {
	
    private String username;
    private Role role;
    private Long employeeID;
    private String message;

    public RegistrationUserResponseDTO() { }

    // Getters
    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
