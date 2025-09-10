package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository <Profesor, Integer> {
    
}
