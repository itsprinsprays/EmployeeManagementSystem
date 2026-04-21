package com.prince.ems.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prince.ems.entity.Department;
import com.prince.ems.entity.Employee;
import com.prince.ems.entity.Role;
import com.prince.ems.entity.Status;
import com.prince.ems.entity.User;
import com.prince.ems.repository.DepartmentRepository;
import com.prince.ems.repository.EmployeeRepository;
import com.prince.ems.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner{
	
	private final UserRepository uRepo;
	private final DepartmentRepository dRepo;
	private final EmployeeRepository eRepo;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UserRepository uRepo,DepartmentRepository dRepo ,EmployeeRepository eRepo, PasswordEncoder passwordEncoder) {
		this.uRepo = uRepo;
		this.dRepo = dRepo;
		this.eRepo = eRepo;
		this.passwordEncoder = passwordEncoder;
	}
	 
	@Transactional
	@Override
	public void run(String... args) {
		
		if(dRepo.count() == 0) {
			System.out.println("1. Creating department");

			Department dept = new Department();
			dept.setName("Information Technology");
			dept.setDescription("Maintenance, Development, Innovation");
			dRepo.save(dept);
			
			System.out.println("2. Creating employee");
			Employee employee = new Employee();
			employee.setName("ADMIN");
			employee.setEmail("admin@gmail.com");
			employee.setSalary(BigDecimal.valueOf(100000));
			employee.setDepartment(dept);
			eRepo.save(employee);
			
			System.out.println("3. Creating user");
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setStatus(Status.ACTIVE);
			admin.setRole(Role.ADMIN);
			admin.setEmployee(employee);		
			uRepo.save(admin);
			
			System.out.println("✅ Default ADMIN created");
		}
		
	}
}
