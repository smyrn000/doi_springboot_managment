package com.doi.business.controller;

import com.doi.business.model.Form;
import com.doi.business.model.User;
import com.doi.business.repository.UserRepository;
import com.doi.business.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/forms")
@CrossOrigin("*")
public class FormController {

    @Autowired
    private FormService formService;
    @Autowired
    private UserRepository userRepository;

    /* Endpoint for creating a new form. */
    @PostMapping("/")
    public ResponseEntity<Form> createForm(@RequestBody Form form, @RequestParam("user_id") Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        form.setUser(user);
        user.setForm(form);
        Form createdForm = formService.createForm(form);
        userRepository.save(user);
        return ResponseEntity.ok(createdForm);
    }
}