package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Materia;



@Repository
public interface DictadoClaseRepository extends JpaRepository<DictadoClase, Integer> {

    @Query("SELECT DC FROM DictadoClase DC WHERE DC.profesor.id = ?1 AND DC.materia.id=?2 AND DC.nivel.nro=?3")
    DictadoClase findByMateriaProfesorNivel(int idProfesor, int idMateria, int idNivel);

    @Query("SELECT DC FROM DictadoClase DC WHERE DC.profesor.id = ?1")
    List <DictadoClase> findByProfesor(int idProfesor);

    @Query("SELECT dc.materia FROM DictadoClase dc WHERE dc.nivel.nro = ?1")
    List <Materia> findMateriaByNivel(int nro);
}
