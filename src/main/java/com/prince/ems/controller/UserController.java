package com.prince.ems.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prince.ems.service.UserService;
import com.prince.ems.dto.user.ChangePasswordRequestDTO;
import com.prince.ems.dto.user.MessageResponseDTO;
import com.prince.ems.dto.user.GetUserResponseDTO;
import com.prince.ems.dto.user.RegistrationUserRequestDTO;
import com.prince.ems.dto.user.RegistrationUserResponseDTO;
import com.prince.ems.dto.user.RoleUpdateRequestDTO;
import com.prince.ems.dto.user.SoftDeleteUserRequestDTO;
import com.prince.ems.dto.user.SoftDeleteUserResponseDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/auth")
@Validated
@RestController
public class UserController {
	
	private final UserService serv;
	
	public UserController(UserService serv) { 
		this.serv = serv;
	}
	

	@PostMapping("/register")
	public ResponseEntity<RegistrationUserResponseDTO> registerUser(@Valid @RequestBody RegistrationUserRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serv.registerUser(dto));
	}
	

	@GetMapping
	public ResponseEntity<Page<GetUserResponseDTO>> getAllAccounts(
									@RequestParam(defaultValue = "5") int size,
									@RequestParam(defaultValue = "0") int page) {
		
		PageRequest pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return ResponseEntity.ok().body(serv.getAll(pageable));
		
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<GetUserResponseDTO> getAccountByID(@PathVariable Long Id) {
		return ResponseEntity.ok().body(serv.getUserByID(Id));
	}
	
	@PatchMapping("/changepassword/{Id}")
	public ResponseEntity<MessageResponseDTO> userChangePassword(@PathVariable Long Id, @RequestBody ChangePasswordRequestDTO dto) {
		return ResponseEntity.ok().body(serv.changePassword(Id, dto));
	}
	
	@PatchMapping("/status/{Id}")
	public ResponseEntity<MessageResponseDTO> setStatus(@PathVariable Long Id, @RequestBody SoftDeleteUserRequestDTO dto) {
		return ResponseEntity.ok().body(serv.setStatus(Id, dto));
	}
	
	@PatchMapping("/role/{Id}")
	public ResponseEntity<MessageResponseDTO> updateRole(@PathVariable Long Id, @RequestBody RoleUpdateRequestDTO dto) {
		return ResponseEntity.ok().body(serv.updateRole(Id, dto));
	}

}
