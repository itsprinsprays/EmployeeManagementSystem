package com.prince.ems.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prince.ems.entity.Role;
import com.prince.ems.entity.User;
import com.prince.ems.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner{
	
	private final UserRepository uRepo;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UserRepository uRepo, PasswordEncoder passwordEncoder) {
		this.uRepo = uRepo;
		this.passwordEncoder = passwordEncoder;
	}
	 
	@Override
	public void run(String... args) {
		
		if(uRepo.count() == 0) {
			User admin = new User();
			
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			
			uRepo.save(admin);
			
			System.out.println("✅ Default ADMIN created");
		}
		
	}
}
