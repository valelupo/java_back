package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Localidad;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer>{
    
    @Query("SELECT l FROM Localidad l WHERE l.miProvincia.id = ?1")
    List<Localidad> buscarPorProvincia(int idProv);
} 