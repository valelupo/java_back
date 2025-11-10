package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
 
    @Query("SELECT m FROM Material m WHERE m.profesor.id = ?1")
    List<Material> findByProfesorId(int idProfesor);
}
