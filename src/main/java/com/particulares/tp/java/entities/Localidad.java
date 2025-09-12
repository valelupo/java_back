package com.particulares.tp.java.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "localidad")
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia miProvincia; 

    //setters y getters 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Provincia getMiProvincia() {
        return miProvincia;
    }
    public void setMiProvincia(Provincia miProvincia) {
        this.miProvincia = miProvincia;
    }

    
    
}
