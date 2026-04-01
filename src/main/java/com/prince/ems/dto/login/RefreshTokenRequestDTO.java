package com.prince.ems.dto.login;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO {
	
	@NotBlank()
	private String refreshToken;
	
	public RefreshTokenRequestDTO() {}
	
	public String getRefreshToken() { return refreshToken; }
	
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

}
