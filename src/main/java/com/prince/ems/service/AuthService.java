package com.prince.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.login.LoginRequestDTO;
import com.prince.ems.dto.login.LoginResponseDTO;
import com.prince.ems.dto.login.RefreshTokenRequestDTO;
import com.prince.ems.dto.login.RefreshTokenResponseDTO;
import com.prince.ems.entity.Status;
import com.prince.ems.entity.User;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.mapper.LoginMapper;
import com.prince.ems.repository.UserRepository;
import com.prince.ems.security.JwtUtil;

import io.jsonwebtoken.Claims;

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
			.orElseThrow(() -> new BadRequestException("Invalid username or password")
					  );
		  
		if(user.getStatus() == Status.INACTIVE)
			throw new BadRequestException("Account is inactive");

		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
					 ));
		} catch(Exception e) {
			throw new BadRequestException("Invalid username or password");
		}
		
		String token = util.generateToken(request.getUsername());
		String refreshToken = util.generateRefreshToken(request.getUsername());
		
		return LoginMapper.loginTokenResponse(token, refreshToken);
		
	}
	
	public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO dto) {
		
		Claims claim = util.extractAllClaims(dto.getRefreshToken());                                                                                                                      
		
		if(!util.validateToken(dto.getRefreshToken()))
			throw new BadRequestException("Invalid Token");
		
		if(!"refresh".equals(claim.get("type")))
			throw new BadRequestException("Invalid token for refresh");
		 
		String username = util.extractUsername(dto.getRefreshToken());
		
		User user = urepo.findByUsername(username)
				.orElseThrow(() -> new BadRequestException("No Username Found"));
		
		if(user.getStatus() == Status.INACTIVE)
			throw new BadRequestException("Account is Inactive");
		
		String newToken = util.generateToken(username);
		     
		return LoginMapper.refreshTokenResponse(newToken);
	}

}
