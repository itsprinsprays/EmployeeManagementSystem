package com.prince.ems.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequestDTO {
	
	
	@NotBlank(message = "Current Password is required")
	String currentPassword;
	
	@NotBlank(message = "New Password is required")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least 1 uppercase letter and 1 number")
	@Size(min = 8)
	@Size(max = 20, message = "Password Maximum is 20")
	String newPassword;
	
	@NotBlank(message = "Confirm Password is required")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least 1 uppercase letter and 1 number")
	@Size(min = 8)
	@Size(max = 20, message = "Password Maximum is 20")
	String confirmNewPassword;
	
	public ChangePasswordRequestDTO() {}
	
	public String getCurrentPassword() { return currentPassword; }
	public String getNewPassword() { return newPassword; }
	public String getConfirmNewPassword() { return confirmNewPassword; }


	public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
	public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
	public void setConfirmNewPassword(String confirmNewPassword) { this.confirmNewPassword = confirmNewPassword; }

}
