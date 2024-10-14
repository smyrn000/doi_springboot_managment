package com.doi.business.controller;

import com.doi.business.model.Form;
import com.doi.business.model.RegistrationDTO;
import com.doi.business.model.User;
import com.doi.business.services.AuthenticationService;
import com.doi.business.services.FormService;
import com.doi.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private FormService formService;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO body)
    {
        return  authenticationService.registerUser(body.getUsername(),body.getPassword(),body.getFull_name(),body.getAfm(),body.getEmail(),body.getAddress(),body.getPhone_num());
    }
    @PostMapping("/register-admin")
    public User registerAdmin(@RequestBody RegistrationDTO body)
    {
        return  authenticationService.registerAdmin(body.getUsername(), body.getPassword());
    }
    @PostMapping("/register-employee")
    public User registerEmployee(@RequestBody RegistrationDTO body)
    {
        return  authenticationService.registerEmployee(body.getUsername(),body.getPassword(),body.getFull_name(),body.getAfm(),body.getEmail(),body.getAddress(),body.getPhone_num());
    }
    @PutMapping("/update/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody RegistrationDTO userDTO) {
        User updatedUser = authenticationService.updateUser(username, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        authenticationService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

}