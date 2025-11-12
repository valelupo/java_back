package com.particulares.tp.java.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "materia") 

public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String nombre;

    @OneToMany(mappedBy = "materia")
    private List<DictadoClase> dictados; 

    //setters y getters 
    public int getId() {
        return id;
    }

    public void setDictados(List<DictadoClase> dictados) {
        this.dictados = dictados;
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

    public List<DictadoClase> getDictados() {
        return dictados;
    } 


}
