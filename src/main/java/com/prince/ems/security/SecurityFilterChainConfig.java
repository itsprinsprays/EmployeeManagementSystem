package com.prince.ems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prince.ems.ratelimiter.RateLimitFilter;


@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
		
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtEntryPoint;
	private final RateLimitFilter rateLimitFilter;
	
	public SecurityFilterChainConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtEntryPoint, RateLimitFilter rateLimitFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtEntryPoint = jwtEntryPoint;
		this.rateLimitFilter = rateLimitFilter;
	}
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth 
						
						//Public Endpoints
						.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
						
						.requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
												
						.requestMatchers(HttpMethod.POST, "/api/auth/register").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/attendance/**").permitAll()
						
						.requestMatchers(HttpMethod.GET, "/api/attendance/**").permitAll()
						
						.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
						
						.requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
						
						.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "HR")
						
						.anyRequest().authenticated()
						)
	            .exceptionHandling(e -> e.authenticationEntryPoint(jwtEntryPoint))
				.addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	

}
