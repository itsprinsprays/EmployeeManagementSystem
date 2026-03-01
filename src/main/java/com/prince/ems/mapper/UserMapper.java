package com.prince.ems.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.dto.user.RegistrationUserResponseDTO;
import com.prince.ems.entity.User;

@Component
public class UserMapper {
	
	public static RegistrationUserResponseDTO registrationResponse(User user) {
		
		RegistrationUserResponseDTO dto = new RegistrationUserResponseDTO();
		
		dto.setUsername(user.getUsername());
		dto.setRole(user.getRole());
		dto.setEmployeeID(user.getEmployee().getId());
		dto.setMessage("Registration Succesfully");
		
		return dto;
		
	}

}
