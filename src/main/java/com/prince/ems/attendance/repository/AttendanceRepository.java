package com.prince.ems.attendance.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prince.ems.entity.Attendance;
import com.prince.ems.entity.AttendanceStatus;

import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	Page<Attendance> findByEmployeeId(Long Id, Pageable pageable);
	Optional<Attendance> findByDateAndEmployeeId(LocalDate date, Long Id);
	Optional<Attendance> findById(Attendance attendance);
	boolean existsByDateAndEmployeeId(LocalDate date, Long Id);
	

}
