package com.particulares.tp.java.dto;

import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.enums.FormaTrabajo;

public class ProfesorConPuntajeDTO {
    private Profesor profesor;
    private Double promedio;
    
    public ProfesorConPuntajeDTO(Profesor profesor, Double promedio) {
        this.profesor = profesor;
        this.promedio = promedio != null ? promedio : 0.0;
    }
    
    public String getNombre() {
        return profesor.getNombre();
    }

    public String getApellido() {
        return profesor.getApellido();
    }

    public double getPrecioXHs() {
        return profesor.getPrecioXHs();
    }

    public FormaTrabajo getFormaTrabajo() {
        return profesor.getFormaTrabajo();
    }

    public int getId() {
        return profesor.getId();
    }

    public double getPromedio() {
        return promedio;
    }

    
}
