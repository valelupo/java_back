package com.particulares.tp.java.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "resenia")

public class Resenia {
    private LocalDateTime fecha;
    private String descripcion;

    @EmbeddedId
    private ReseniaId id; 

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("profesorId")
    private Profesor profesor;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("alumnoId")
    private Alumno alumno;

    //getters y setters 
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ReseniaId getId() {
        return id;
    }

    public void setId(ReseniaId id) {
        this.id = id;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


    
}
