package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.entities.Provincia;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
    @Query("SELECT p FROM Persona p WHERE p.email = ?1")
    Persona buscarPorEmail(String email);

    @Query("SELECT p.miLocalidad.miProvincia FROM Persona p")
    List<Provincia> buscarProvincias();

    @Query("SELECT p.miLocalidad FROM Persona p")
    List<Localidad> buscarLocalidades();
}
