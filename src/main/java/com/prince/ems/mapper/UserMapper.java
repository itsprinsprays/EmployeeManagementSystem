package com.prince.ems.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.prince.ems.dto.user.ChangePasswordResponseDTO;
import com.prince.ems.dto.user.GetUserResponseDTO;
import com.prince.ems.dto.user.RegistrationUserResponseDTO;
import com.prince.ems.dto.user.SoftDeleteUserResponseDTO;
import com.prince.ems.entity.User;

@Component
public class UserMapper {
	
	public static RegistrationUserResponseDTO registrationResponse(User user) {
		
		RegistrationUserResponseDTO dto = new RegistrationUserResponseDTO();
		
		dto.setUsername(user.getUsername());
		dto.setRole(user.getRole());
		dto.setEmployeeID(user.getEmployee().getId());
		dto.setMessage("Registration Succesfully");
		
		return dto;
		
	}
	
	public static Page<GetUserResponseDTO> getResponse(Page<User> user) {
		Page<GetUserResponseDTO> dto = user.map(e -> new GetUserResponseDTO(
				e.getUsername(),
				e.getRole(),
				e.getId()
			));
		
		return dto;
		
	}
	
	public static GetUserResponseDTO getResponse(User user) {
		GetUserResponseDTO dto = new GetUserResponseDTO();
		
		dto.setUsername(user.getUsername());
		dto.setRole(user.getRole());
		dto.setEmployeeID(user.getEmployee().getId());
		
		return dto;
		
	}
	
	
	public static ChangePasswordResponseDTO changePasswordResponse() {
		ChangePasswordResponseDTO dto = new ChangePasswordResponseDTO();
		
		dto.setMessage("Changed Password Succesfully");
		return dto;
	}
	
	public static SoftDeleteUserResponseDTO statusResponse() {
		SoftDeleteUserResponseDTO dto = new SoftDeleteUserResponseDTO();
		
		dto.setMessage("Updating Succesfully");
		return dto;
	}
	


}
