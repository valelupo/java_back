package com.particulares.tp.java.dto;

import java.util.List;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.enums.FormaTrabajo;

public class ProfesorConPuntajeDTO {
    private Profesor profesor;
    private Double promedio;
    private List<DictadoClase> dictados;
    
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

    public Provincia getProvincia() {
        return profesor.getMiLocalidad().getMiProvincia();
    }

    public Localidad getLocalidad() {
        return profesor.getMiLocalidad();
    }

    public void setDictados(List<DictadoClase> dictados) {
        this.dictados = dictados;
    }

    public List<DictadoClase> getDictados() {
        return dictados;
    }

}
