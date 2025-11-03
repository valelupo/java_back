package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.DictadoClase;



@Repository
public interface DictadoClaseRepository extends JpaRepository<DictadoClase, Integer> {

    @Query("SELECT DC FROM DictadoClase DC WHERE DC.profesor.id = ?1, DC.materia.id=?2, DC.nivel.id=?3")
    DictadoClase findByMateriaProfesorNivel(int idProfesor, int idMateria, int idNivel);
}
