package com.prince.ems.dto;

import java.time.LocalDateTime;

import com.prince.ems.entity.Status;




public class CreateDepartmentResponseDTO {
	
	private Long Id;
	
	private String name;
	
	private String description;
	
	private Status status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private String statusMessage;
	
	public CreateDepartmentResponseDTO() { }
	
	public Long getID() { return Id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public Status getStatus() { return status; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	public String getStatusMessage() { return statusMessage; }
	
	public void setID(Long Id) { this.Id = Id; }
	public void setName(String name) { this.name = name; }
	public void setDescription(String description) { this.description = description; }
	public void setStatus(Status status) { this.status = status; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; } 

}
