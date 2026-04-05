package com.prince.ems.ratelimiter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prince.ems.security.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter{
	
	private final TokenBucketService tokenBucketService;
	private final JwtUtil jwtUtil;
	
	public RateLimitFilter(TokenBucketService tokenBucketService, JwtUtil jwtUtil) {
		this.tokenBucketService = tokenBucketService;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
													throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		String token = null;
		String username = null;
		
		if(header != null && header.startsWith("Bearer")) {
			token = header.substring(7);
			
			username = jwtUtil.extractUsername(token);
		}
		
		String ip = username; //replace with userID
		
		if(ip == null) {
			ip = request.getRemoteAddr();
		}
		
		if(!tokenBucketService.allowRequest(ip)) {
			response.setStatus(429);
			response.getWriter().write("Too many Requests");
			return;
		}
		
		filterChain.doFilter(request, response);  
		
	}
		

}
