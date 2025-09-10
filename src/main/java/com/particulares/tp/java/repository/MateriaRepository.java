package com.particulares.tp.java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Materia;

@Repository
public interface MateriaRepository {

    void save(Materia materia);

	Optional<Materia> findById(int id);

    void delete(Materia materia);

    Materia getReferenceById(int id);

    List<Materia> findAll();


    
} 
