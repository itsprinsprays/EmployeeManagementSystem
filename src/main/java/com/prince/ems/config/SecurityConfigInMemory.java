package com.prince.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigInMemory {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(encoder.encode("admin123"))
				.roles("ADMIN")
				.build();
		
		UserDetails hr = User.builder()
				.username("hr")
				.password(encoder.encode("hr123"))
				.roles("HR")
				.build();
		
		UserDetails employee = User.builder()
				.username("employee")
				.password(encoder.encode("employee123"))
				.roles("EMPLOYEE")
				.build();
		
		return new InMemoryUserDetailsManager(admin, hr, employee);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.POST, "employee/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PATCH, "employee/**").hasRole("ADMIN")
					
					.requestMatchers(HttpMethod.GET, "employee/**").hasAnyRole("ADMIN","HR")
					
					.anyRequest().authenticated()
					)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
