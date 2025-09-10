package com.particulares.tp.java.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "dictado_clase")
public class DictadoClase {
    @EmbeddedId
    private DictadoClaseId id;

    @ManyToOne
    @MapsId("profesorId")
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne
    @MapsId("nivelId")
    @JoinColumn(name = "nivel_id")
    private Nivel nivel;

    @ManyToOne
    @MapsId("materiaId")
    @JoinColumn(name = "materia_id")
    private Materia materia;


    // Getters y setters
    public DictadoClaseId getId() {
        return id;
    }

    public void setId(DictadoClaseId id) {
        this.id = id;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }  
}
