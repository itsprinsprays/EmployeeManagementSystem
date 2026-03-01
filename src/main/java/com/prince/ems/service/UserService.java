package com.prince.ems.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prince.ems.dto.user.RegistrationUserRequestDTO;
import com.prince.ems.dto.user.RegistrationUserResponseDTO;
import com.prince.ems.entity.Employee;
import com.prince.ems.entity.User;
import com.prince.ems.repository.EmployeeRepository;
import com.prince.ems.repository.UserRepository;
import com.prince.ems.exception.BadRequestException;
import com.prince.ems.exception.DuplicateResponseException;
import com.prince.ems.exception.ResourceNotFoundException;
import com.prince.ems.mapper.UserMapper;


@Service
public class UserService {


	
	private final UserRepository urepo;
	private final EmployeeRepository erepo;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository urepo, EmployeeRepository erepo, PasswordEncoder passwordEncoder) {
		this.urepo = urepo;
		this.erepo = erepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public RegistrationUserResponseDTO registerUser(RegistrationUserRequestDTO dto) {
		
		if(urepo.existsByUsername(dto.getUsername()))
			throw new DuplicateResponseException("Username Already Existing");
				
		if(dto.getPassword().length() < 8)
			throw new BadRequestException("Password must be at least 8 characters");
		
		Employee employee = erepo.findById(dto.getEmployeeID())
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + dto.getEmployeeID() + " is not existing"));
		
		
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		user.setEmployee(employee);
		
		urepo.save(user);
		
		return UserMapper.registrationResponse(user);
		
	}
	
}
