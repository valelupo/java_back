package com.particulares.tp.java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Integer>{

    @Query("SELECT m FROM Materia m WHERE m.nombre = ?1")
    Materia findByNombre(String nombre);
} 
