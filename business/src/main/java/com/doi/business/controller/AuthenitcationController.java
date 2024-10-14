package com.doi.business.controller;

import com.doi.business.model.LoginDTO;
import com.doi.business.model.RegistrationDTO;
import com.doi.business.model.User;
import com.doi.business.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenitcationController {

    @Autowired
    private AuthenticationService authenticationService;

    /* Endpoint for user login. */
    @PostMapping("/login")
    public LoginDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
