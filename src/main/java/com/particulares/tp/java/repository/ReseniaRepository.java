package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Resenia;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Integer> {

    @Query("SELECT AVG(r.puntaje) FROM Resenia r WHERE r.profesor.id = ?1")
    Double findPromedioByProfesorId(int idProfesor);
} 