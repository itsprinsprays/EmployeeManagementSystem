package com.prince.ems.entity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name = "employeeId", nullable = false)
	private Employee employee;
	
	private LocalDate date;
	
	@Column(nullable = false)
	private LocalTime timeIn;
	
	private LocalTime timeOut;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false) 	
	private AttendanceStatus status;
	
	private Long totalHours;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Attendance() { }
	
	   // --- Getters ---
    public long getId() { return Id; }
    public Employee getEmployee() { return employee; }
    public LocalDate getDate() { return date; }
    public LocalTime getTimeIn() { return timeIn; }
    public LocalTime getTimeOut() { return timeOut; }
    public AttendanceStatus getStatus() { return status; }
    public Long getTotalHours() { return totalHours; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // --- Setters ---
    public void setId(long Id) { this.Id = Id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    public void setTotalHours(Long totalHours) { this.totalHours = totalHours; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}
