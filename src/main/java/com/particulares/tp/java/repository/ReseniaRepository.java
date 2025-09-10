package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Resenia;
import com.particulares.tp.java.entities.ReseniaId;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, ReseniaId> {

    
} 