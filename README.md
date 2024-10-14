# DOI Fullstack Project
### Overview
This project is a full-stack application for the Δημόσια Οικονομική Υπηρεσία. It features a Spring Boot backend with Gradle for building and managing dependencies, and a Vue.js frontend for interactive user interfaces. The application is designed to manage user registrations, process form submissions, and provide administrative functionalities.

### Features
- User Registration and Authentication: Secure registration and login system for users.
- Form Submission: Users can submit and track forms, which are then reviewed by administrators.
- Admin Panel: Admins have the capability to review form submissions, manage user accounts, and perform administrative tasks.

## Getting Started
### Prerequisites
- JDK 11 or later
- Node.js and npm
- MySQL Database
- IntelliJ (Recommended)

### Installation
- Clone the Repository:
- Create database
- Create Tables
  	- role
  	  ```sql
  	  CREATE TABLE `role` (
	  `id` int(10) NOT NULL,
	  `authority` varchar(255) DEFAULT NULL
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

  	  ALTER TABLE `role`
  		ADD PRIMARY KEY (`id`);

  	  ALTER TABLE `role`
  		MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
  	  COMMIT;
  	  ```
 	- user
    	```sql
  	  CREATE TABLE `user` (
	  `id` int(10) NOT NULL,
	  `username` varchar(20) NOT NULL,
	  `password` varchar(255) NOT NULL,
	  `full_name` varchar(50) DEFAULT NULL,
	  `AFM` int(20) DEFAULT NULL,
	  `role_id` int(20) NOT NULL,
	  `email` varchar(20) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `phone_num` varchar(15) DEFAULT NULL
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

     		ALTER TABLE `user`
	  ADD PRIMARY KEY (`id`),
	  ADD UNIQUE KEY `username` (`username`),
	  ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`);

	     ALTER TABLE `user`
	  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

     	ALTER TABLE `user`
  		ADD CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
		COMMIT;
  	  ```
  	- form
  	  ```sql
  	  CREATE TABLE `form` (
	  `id` int(10) NOT NULL,
	  `purpose` varchar(50) NOT NULL,
	  `katastatiko` varchar(100) NOT NULL,
	  `HQ` varchar(30) NOT NULL,
	  `members` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
	  `entry_date` datetime(6) DEFAULT NULL,
	  `status` varchar(20) NOT NULL,
	  `user_id` int(255) NOT NULL
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

	  ALTER TABLE `form`
	  ADD PRIMARY KEY (`id`),
	  ADD UNIQUE KEY `user_id` (`user_id`);
	
	  ALTER TABLE `form`
	  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
	
	  ALTER TABLE `form`
	  ADD CONSTRAINT `FKsniuo4i0n35d0lw0pjlc2iqwe` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
  	  COMMIT;
  	  ```
  

### Backend Setup:
- Navigate to the backend directory: 
  -  ```cd BusinessProject\business```
  
- Update the application.properties to configure the database connection.
  	- ex.
  	  	```
	  	spring.datasource.url=jdbc:mysql://localhost:3306/db_doi
		spring.datasource.username=root
		spring.datasource.password=root
		spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
		spring.jpa.hibernate.ddl-auto=update
	
- Build the project using Gradle:
  - ```./gradlew build```
  
- Run the Spring Boot application:
  - ```./gradlew bootRun```
  
### Frontend Setup:
- Navigate to the frontend directory:
  - ```cd BusinessProject\business-front```
  - ```npm run dev```
  - The frontend will be served on http://localhost:5173/
  
## Usage
- The backend application will be accessible on http://localhost:8080 (default port).
- In case of npm run dev, the application will be accessible on http://localhost:5173 (better choice)
- Users can:
  - Log in, View their details and submit forms.
- Administrators can:
  - Log in, view, update or delete users, view, accept or decline forms and have access to the admin panel to manage the application.
#### On Project start, 2 user roles are created and 1 admin user with username: ```admin``` and password: ```admin```
```java
@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder)
	{
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

			// Create admin and user Role
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));
			// Create Admin account
			User admin = new User("admin", passwordEncoder.encode("admin"), adminRole);
			userRepository.save(admin);
		};

	}
```
