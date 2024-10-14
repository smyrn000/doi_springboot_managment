package com.doi.business.controller;

import com.doi.business.model.Form;
import com.doi.business.model.RegistrationDTO;
import com.doi.business.model.User;
import com.doi.business.services.FormService;
import com.doi.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            RegistrationDTO userDTO = userService.convertToDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{username}/hasSubmittedForm")
    public ResponseEntity<?> hasUserSubmittedForm(@PathVariable String username) {
        boolean hasSubmitted = userService.hasUserSubmittedForm(username);
        return ResponseEntity.ok(hasSubmitted);
    }



}
