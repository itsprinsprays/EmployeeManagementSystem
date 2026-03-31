package com.prince.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmployeeId(Long Id);

}
