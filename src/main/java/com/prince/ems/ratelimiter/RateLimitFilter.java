package com.prince.ems.ratelimiter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter{
	
	private final TokenBucketService tokenBucketService;
	
	public RateLimitFilter(TokenBucketService tokenBucketService) {
		this.tokenBucketService = tokenBucketService;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
													throws ServletException, IOException {
		
		String ip = request.getRemoteAddr(); //replace with userID
		
		if(!tokenBucketService.allowRequest(ip)) {
			response.setStatus(429);
			response.getWriter().write("Too many Requests");
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
		

}
