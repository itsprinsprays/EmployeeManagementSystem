package com.prince.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.login.LoginRequestDTO;
import com.prince.ems.dto.login.LoginResponseDTO;
import com.prince.ems.entity.User;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.mapper.LoginMapper;
import com.prince.ems.repository.UserRepository;
import com.prince.ems.security.JwtUtil;

@Service
public class AuthService {
	
	private UserRepository urepo;
	
	private AuthenticationManager authenticationManager;
	
	private JwtUtil util;
	
	@Autowired
	public AuthService(AuthenticationManager authenticationManager, JwtUtil util, UserRepository urepo) {
		this.authenticationManager = authenticationManager;
		this.util = util;
		this.urepo = urepo;
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		
		User user = urepo.findByUsername(request.getUsername())
			.orElseThrow(() -> new BadRequestException("User not found")
					  );
//		
//		if(request.getPassword() != user.getPassword())
//			throw new BadRequestException("Wrong Password");

		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
					 ));
			
		String token = util.generateToken(request.getUsername());
		
		return LoginMapper.tokenResponse(token);
		
	}

}
