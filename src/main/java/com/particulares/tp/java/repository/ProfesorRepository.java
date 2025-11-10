package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.dto.ProfesorConPuntajeDTO;
import com.particulares.tp.java.entities.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository <Profesor, Integer> {
    @Query(value = """
    SELECT new com.particulares.tp.java.dto.ProfesorConPuntajeDTO(p, AVG(r.puntaje))
    FROM Profesor p
    LEFT JOIN Resenia r ON p = r.profesor
    GROUP BY p
    """)
    List<ProfesorConPuntajeDTO> profesoresConPuntaje();

    @Query(value = """
    SELECT new com.particulares.tp.java.dto.ProfesorConPuntajeDTO(p, AVG(r.puntaje))
    FROM Profesor p
    LEFT JOIN Resenia r ON p = r.profesor
    GROUP BY p
    ORDER BY AVG(r.puntaje) DESC
    """)
    List<ProfesorConPuntajeDTO> profesoresConPuntajeOrdenados();
}
