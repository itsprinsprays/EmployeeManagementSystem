package com.prince.ems.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.dto.login.LoginResponseDTO;

@Component
public class LoginMapper {
	
	public static LoginResponseDTO tokenResponse(String token) {
		
		LoginResponseDTO dto = new LoginResponseDTO();
		
		dto.setToken(token);
		
		return dto;
		
	}

}
