package com.particulares.tp.java.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "localidades")

public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod; 
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia miProvincia; 

    //setters y getters 
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
