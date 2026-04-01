package com.prince.ems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
		
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtEntryPoint;
	
	public SecurityFilterChainConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtEntryPoint) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.jwtEntryPoint = jwtEntryPoint;
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
						
						.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
						
						.requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
						
						.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "HR")
						
						.anyRequest().authenticated()
						)
	            .exceptionHandling(e -> e.authenticationEntryPoint(jwtEntryPoint))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	

}
