package com.doi.business.controller;

import com.doi.business.model.Form;
import com.doi.business.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private FormService formService;

    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }
    @GetMapping("/forms/{formId}")
    public ResponseEntity<Form> getFormFromId(@PathVariable Integer formId) {
        Form form = formService.getById(formId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return  ResponseEntity.ok(form);
    }
    @PostMapping("/accept/{formId}")
    public Integer acceptForm(@PathVariable Integer formId) {
        Integer afm = formService.acceptForm(formId);
        return afm;
    }

    @PostMapping("/reject/{formId}")
    public ResponseEntity<Form> rejectForm(@PathVariable Integer formId) {
        Form form = formService.rejectForm(formId);
        return ResponseEntity.ok(form);
    }
}
