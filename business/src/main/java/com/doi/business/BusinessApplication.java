package com.doi.business;

import com.doi.business.model.Role;
import com.doi.business.model.User;
import com.doi.business.repository.RoleRepository;
import com.doi.business.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class BusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessApplication.class, args);
	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder)
	{
		return args -> {
			// Check and create the "ADMIN" role if it doesn't exist
			Optional<Role> adminRoleOptional = roleRepository.findByAuthority("ADMIN");
			Role adminRole;
			if (!adminRoleOptional.isPresent()) {
				adminRole = roleRepository.save(new Role("ADMIN"));
			} else {
				adminRole = adminRoleOptional.get();
			}

// Check and create the "USER" role if it doesn't exist
			Optional<Role> userRoleOptional = roleRepository.findByAuthority("USER");
			if (!userRoleOptional.isPresent()) {
				roleRepository.save(new Role("USER"));
			}

// Check and create the "EMPLOYEE" role if it doesn't exist
			Optional<Role> employeeRoleOptional = roleRepository.findByAuthority("EMPLOYEE");
			if (!employeeRoleOptional.isPresent()) {
				roleRepository.save(new Role("EMPLOYEE"));
			}

// Check if the admin user exists before creating a new admin account
			Optional<User> adminUserOptional = userRepository.findByUsername("admin");
			if (!adminUserOptional.isPresent()) {
				// Assuming passwordEncoder is already defined and initialized
				User admin = new User("admin", passwordEncoder.encode("admin"), adminRole);
				userRepository.save(admin);
			}




		};

	}
}
