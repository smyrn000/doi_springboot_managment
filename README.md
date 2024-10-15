# ΔΟΥ Management System
A web application designed to mimic the functionality of the Greek public administration system, ΔΟΥ (Greek Tax Authority), allowing for admin, staff, and user roles with distinct capabilities. Built using **Spring Boot** for the backend and **Vue.js** for the frontend, this app allows:

-   **Admins** to manage users (create, update, delete).
-   **Staff** to view and process forms submitted by users (approve or decline).
-   **Users** to manage personal information and submit forms for processing.

 ## Table of Contents

-   [Features](#features)
-   [Technology Stack](#technology-stack)
-   [Prerequisites](#prerequisites)
-   [Getting Started](#getting-started)
    -   [Database Configuration](#database-configuration)
    -   [Clone the repository](#clone-the-repository)
    -   [Backend Setup (Spring Boot)](#backend-setup-spring-boot)
    -   [Frontend Setup (Vue.js)](#frontend-setup-vuejs)
-   [Running the Application](#running-the-application)
-   [Role Descriptions](#role-descriptions)
-   [Screenshots](#screenshots)
-   [Contributing](#contributing)
-   [License](#license)

## Features

-   **Admin Dashboard**: Manage users, including creating, updating, and deleting users.
-   **Staff Dashboard**: Review, approve, or decline user-submitted forms.
-   **User Portal**: View personal information and submit forms for staff review.
-   Authentication and role-based access control for different features.

## Technology Stack

-   **Backend**: Spring Boot, Spring Security, JPA, MySQL
-   **Frontend**: Vue.js, Vue Router
-   **Build Tool**: Gradle
-   **Database**:  MySQL
-   **Authentication**: JWT-based authentication

## Prerequisites

Ensure you have the following installed before setting up the project:

-   **JDK 17 or later**
-   **Node.js & npm** (for Vue.js frontend)
-   **Gradle** (for backend build)
-   **MySQL** ( You can use Mariadb for easy setup)

## Getting Started

### Database Configuration

1. Using MySQL create database ```db_doi```

3. Create the following tables using these queries: 
- **role**
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
 - **user**
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
 - **form**
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

Ensure you have a MySQL server running and the `db_doi` database created before starting the application.

### Clone the repository

- clone repo

### Backend Setup (Spring Boot)

1.  Navigate to the backend directory:

```bash
cd business
```
2.  Run the following command to build and start the Spring Boot application:

bash

Copy code

`./gradlew bootRun` 

3.  The backend will start on http://localhost:8080 by default.

### Frontend Setup (Vue.js)

1.  Navigate to the frontend directory:

bash

Copy code

`cd frontend` 

2.  Install dependencies:

bash

Copy code

`npm install` 

3.  Start the Vue.js frontend:

bash

Copy code

`npm run serve` 

4.  The frontend will be available at http://localhost:3000.

## Running the Application

To run both frontend and backend:

1.  Start the backend server using Gradle as described earlier.
2.  Start the frontend server using npm.
3.  The application can be accessed via the frontend URL http://localhost:3000, and the backend APIs are available on http://localhost:8080.

## Role Descriptions

### Admin

-   Can create, update, and delete users.
-   Access the Admin Dashboard to manage system-wide settings.

### Staff

-   Can view submitted forms by users.
-   Can approve or decline forms based on user information.

### User

-   Can view personal information.
-   Can submit forms to the staff for approval.

## Screenshots

Here, you can add screenshots of the application in action, such as:

-   **Admin Dashboard**
-   **Staff Dashboard**
-   **User Form Submission**

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

1.  Fork the repository.
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a pull request.
