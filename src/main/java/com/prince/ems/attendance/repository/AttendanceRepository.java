package com.prince.ems.attendance.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	Optional<Attendance> findByEmployeeId(Long Id);
	Optional<Attendance> findById(Attendance attendance);
	boolean existsByDateAndEmployeeId(LocalDate date, Long Id);
	boolean existsByTimein(LocalTime time);

}
