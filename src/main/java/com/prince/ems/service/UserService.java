package com.prince.ems.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.prince.ems.dto.user.ChangePasswordRequestDTO;
import com.prince.ems.dto.user.ChangePasswordResponseDTO;
import com.prince.ems.dto.user.GetUserResponseDTO;


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
	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public RegistrationUserResponseDTO registerUser(RegistrationUserRequestDTO dto) {
		
		if(urepo.existsByUsername(dto.getUsername()))
			throw new DuplicateResponseException("Username Already Existing");
				
		
		Employee employee = erepo.findById(dto.getEmployeeID())
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + dto.getEmployeeID() + " is not existing"));
		
		if(!employee.getEmail().equalsIgnoreCase(dto.getUsername()))
			throw new BadRequestException("Email does not match the selected employee ID");
		
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		user.setEmployee(employee);
		
		urepo.save(user);
			
		return UserMapper.registrationResponse(user);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public Page<GetUserResponseDTO> getAll(Pageable page){
		Page<User> user = urepo.findAll(page);
		return UserMapper.getResponse(user);		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public ChangePasswordResponseDTO changePassword(Long Id, ChangePasswordRequestDTO dto) {
		
		User user = urepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Employe with ID " + Id + "is not existing"));
		
		if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) 
				throw new BadRequestException("Current Password is incorrect");
		
		if(passwordEncoder.matches(dto.getNewPassword(), user.getPassword()))
				throw new DuplicateResponseException("New password cannot be the same as the old password.");
		
		if(!dto.getNewPassword().equals(dto.getConfirmNewPassword()))
				throw new BadRequestException("Passwords do not match.");
		
		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		urepo.save(user);
		
		return UserMapper.changePasswordResponse();
		
	}
	
	
	
}
