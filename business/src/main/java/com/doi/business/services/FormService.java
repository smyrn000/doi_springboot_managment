package com.doi.business.services;

import com.doi.business.model.Form;
import com.doi.business.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }
    public Optional<Form> getById(Integer id) {
        return formRepository.findById(id);
    }

    /* Creates a new form with a default status. */
    public Form createForm(Form form) {
        form.setStatus("Σε Επεξεργασία");
        return formRepository.save(form);
    }

    /*  Accepts a form and assigns a random number (AFM). */
    public Integer acceptForm(Integer formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        form.setStatus("Εγκρίθηκε");
        Random rand = new Random();
        int firstDigit = rand.nextInt(9) + 1;
        int randomNumber = firstDigit * 100_000_000 + rand.nextInt(100_000_000);
        form.getUser().setAfm(randomNumber);
        formRepository.save(form);
        return randomNumber;
    }

    /* Marks a form as rejected. */
    public Form rejectForm(Integer formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        form.setStatus("Απορρίφθηκε");
        return formRepository.save(form);
    }
}