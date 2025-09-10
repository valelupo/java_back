package com.particulares.tp.java.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReseniaId implements Serializable {
    private int profesorId;
    private int alumnoId; 




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReseniaId)) return false;
        ReseniaId that = (ReseniaId) o;
        return profesorId == that.profesorId &&
            alumnoId == that.alumnoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesorId, alumnoId);
    }
}
    

