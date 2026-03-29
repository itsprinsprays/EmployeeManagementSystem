package com.prince.ems.dto.user;

import com.prince.ems.entity.Role;

public class GetUserResponseDTO {
	
    private String username;
    private Role role;
    private Long employeeID;

    public GetUserResponseDTO(String username, Role role, Long employeeID) {
    	this.username = username;
    	this.role = role;
    	this.employeeID = employeeID;
    }

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



}
