package com.particulares.tp.java.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "nivel") 
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nro; 
    private String descripcion;

    @OneToMany(mappedBy = "nivel")
    private List<DictadoClase> dictados;

    //setters y getters 
    public int getNro() {
        return nro;
    }
    public void setNro(int nro) {
        this.nro = nro;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<DictadoClase> getDictados() {
        return dictados;
    }
    
}
