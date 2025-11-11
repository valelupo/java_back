package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Nivel;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer>{
    
    @Query("SELECT n FROM Nivel n WHERE n.descripcion = ?1")
    Nivel findByDescripcion(String descripcion);
}
