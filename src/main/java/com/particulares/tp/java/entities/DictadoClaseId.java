package com.particulares.tp.java.entities;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DictadoClaseId {
    private int profesorId;
    private int nivelId;
    private int materiaId;

    // Constructor vacío
    public DictadoClaseId() {}

    public DictadoClaseId(int profesorId, int nivelId, int materiaId) {
        this.profesorId = profesorId;
        this.nivelId = nivelId;
        this.materiaId = materiaId;
    }

    // Getters y setters
    public int getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    // equals y hashCode (¡importantes para JPA!)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DictadoClaseId)) return false;
        DictadoClaseId that = (DictadoClaseId) o;
        return Objects.equals(profesorId, that.profesorId) &&
            Objects.equals(nivelId, that.nivelId) &&
            Objects.equals(materiaId, that.materiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesorId, nivelId, materiaId);
    }    
}
