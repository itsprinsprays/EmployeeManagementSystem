package com.prince.ems.dto.user;

import com.prince.ems.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class RoleUpdateRequestDTO {
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public RoleUpdateRequestDTO() { }
	
	public Role getRole() { return role; }
	
	public void setRole(Role role) { this.role = role; }

}
