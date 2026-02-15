package com.prince.ems.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = false)
	private String email;
	
	@Column(nullable = false) 
	private BigDecimal salary;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;
	
	@Column(nullable = false)
	private LocalDate hireDate;
	
	@ManyToOne
	@JoinColumn(name = "departmentId", nullable = false)
	private Department department;
	
	@CreationTimestamp
	@Column(nullable = false) 
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	public Employee() { }
	
	public Long getID() { return id; }
	public String getName() { return name; }
	public String getEmail() { return email; }
	public BigDecimal getSalary() { return salary; }
	public Status getStatus() { return status; }
	public LocalDate getHireDate() { return hireDate; }
	public Department department() { return department; } 
	public LocalDateTime getCreatedAt() { return createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	
	public void setID(Long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setEmail(String email) { this.email = email; } 
	public void setSalary(BigDecimal salary) { this.salary = salary; }
	public void setStatus(Status status) { this.status = status; }
	public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
	public void setDepartment(Department department) { this.department = department; }
	public void setGetCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
	

}
