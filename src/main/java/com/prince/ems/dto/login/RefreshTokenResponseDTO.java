package com.prince.ems.dto.login;

public class RefreshTokenResponseDTO {
	
	private String accessToken;
	
	public RefreshTokenResponseDTO() { }
	
	public String getAccessToken() { return accessToken; }
	
	public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}

