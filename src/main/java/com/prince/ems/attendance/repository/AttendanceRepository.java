package com.prince.ems.attendance.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	Optional<Attendance> findById(Attendance attendance);
	boolean existsByEmployeeId(Long Id);
	boolean existsByDateAndEmployeeId(LocalDate date, Long Id);

}
