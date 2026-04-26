package com.prince.ems.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.prince.ems.entity.AttendanceStatus;
import com.prince.ems.entity.Employee;

public class TimeInResponseDTO {
	

	private Long Id;
	
	private Employee employee;
	
	private String employeeName;
	
	private LocalDate date = LocalDate.now();
	
	private LocalTime timeIn;
	
	private LocalTime timeOut;
	
	private AttendanceStatus status;
	
	private Long totalHours;
	
	private LocalDateTime updatedAt;
	
	public TimeInResponseDTO() { }
	
	// --- Getters ---
    public Long getId() { return Id; }
    public Employee getEmployee() { return employee; }
    public String getEmployeeName() { return employeeName; }
    public LocalDate getDate() { return date; }
    public LocalTime getTimeIn() { return timeIn; }
    public LocalTime getTimeOut() { return timeOut; }
    public AttendanceStatus getStatus() { return status; }
    public Long getTotalHours() { return totalHours; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // --- Setters ---
    public void setId(Long id) { this.Id = id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    public void setTotalHours(Long totalHours) { this.totalHours = totalHours; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
	
	

}
