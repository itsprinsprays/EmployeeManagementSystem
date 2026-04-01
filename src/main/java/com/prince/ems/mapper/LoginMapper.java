package com.prince.ems.mapper;

import org.springframework.stereotype.Component;

import com.prince.ems.dto.login.LoginResponseDTO;
import com.prince.ems.dto.login.RefreshTokenResponseDTO;

@Component
public class LoginMapper {
	
	public static LoginResponseDTO loginTokenResponse(String token, String refreshToken) {
		
		LoginResponseDTO dto = new LoginResponseDTO();
		
		dto.setAccessToken(token);
		dto.setRefreshToken(refreshToken);
		
		return dto;
		
	}
	
	public static RefreshTokenResponseDTO refreshTokenResponse(String token) {
		
		RefreshTokenResponseDTO dto = new RefreshTokenResponseDTO();
		
		dto.setAccessToken(token);
		
		return dto;
		
	}


}
