package com.prince.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.login.LoginRequestDTO;
import com.prince.ems.dto.login.LoginResponseDTO;
import com.prince.ems.entity.Status;
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
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthService(AuthenticationManager authenticationManager, JwtUtil util, UserRepository urepo, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.util = util;
		this.urepo = urepo;
		this.passwordEncoder = passwordEncoder; 
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		
		User user = urepo.findByUsername(request.getUsername())
			.orElseThrow(() -> new BadRequestException("User not found")
					  );
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
			throw new BadRequestException("Wrong Password");
		
		if(user.getStatus() == Status.INACTIVE)
			throw new BadRequestException("Account is inactive");

		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
					 ));
			
		String token = util.generateToken(request.getUsername());
		
		return LoginMapper.tokenResponse(token);
		
	}

}
