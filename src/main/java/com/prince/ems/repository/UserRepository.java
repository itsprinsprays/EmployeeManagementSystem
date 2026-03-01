package com.prince.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prince.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByUsername(String username);

}
