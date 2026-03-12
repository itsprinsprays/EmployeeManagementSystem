package com.prince.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.login.LoginRequestDTO;
import com.prince.ems.dto.login.LoginResponseDTO;
import com.prince.ems.mapper.LoginMapper;
import com.prince.ems.security.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil util;
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
						)
				);
		
		String token = util.generateToken(request.getUsername());
		
		return LoginMapper.tokenResponse(token);
		
	}

}
