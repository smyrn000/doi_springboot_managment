package com.doi.business.repository;

import com.doi.business.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
    Optional<Form> findById(Integer Id);
}

