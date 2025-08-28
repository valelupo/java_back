package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
}
