package com.prince.ems.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.service.UserService;
import com.prince.ems.dto.user.RegistrationUserRequestDTO;
import com.prince.ems.dto.user.RegistrationUserResponseDTO;

import jakarta.validation.Valid;

@RequestMapping("/user")
@Validated
@RestController
public class UserController {
	
	private final UserService serv;
	
	public UserController(UserService serv) {
		this.serv = serv;
	}
	
	@PostMapping
	public ResponseEntity<RegistrationUserResponseDTO> registerUser(@Valid @RequestBody RegistrationUserRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serv.registerUser(dto));
	}

}
