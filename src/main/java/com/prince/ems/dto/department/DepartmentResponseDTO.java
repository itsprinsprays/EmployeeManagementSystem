package com.prince.ems.dto.department;

import java.time.LocalDateTime;

import com.prince.ems.entity.Status;

public class DepartmentResponseDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Status status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
		
	private String statusMessage;
	
	private Long departmentID;
	
	public DepartmentResponseDTO() { }
	
	public Long getID() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public Status getStatus() { return status; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	public String getStatusMessage() { return statusMessage; }
	public Long getDepartmentID() { return departmentID; }

	
	public void setID(Long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setDescription(String description) { this.description = description; }
	public void setStatus(Status status) { this.status = status; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; } 
	public void setDepartmentID(Long departmentID) { this.departmentID = departmentID; }


}
