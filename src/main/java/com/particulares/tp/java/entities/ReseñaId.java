package com.particulares.tp.java.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReseñaId implements Serializable {
    private int profesorId;
    private int estudianteId; 
    
}
