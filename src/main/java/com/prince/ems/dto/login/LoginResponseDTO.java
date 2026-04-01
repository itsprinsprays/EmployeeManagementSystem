package com.prince.ems.dto.login;

public class LoginResponseDTO {
	
	private String accessToken;
	private String refreshToken;
	
	public LoginResponseDTO() { }
	
	public String getAccessToken() { return accessToken; }
	public String getRefreshToken() { return refreshToken; }
	
	public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
