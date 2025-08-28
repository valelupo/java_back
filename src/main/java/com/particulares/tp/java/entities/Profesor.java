package com.particulares.tp.java.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor extends Persona{
    private String telefono;
    private String formaTrabajo;
    private String infoAcademica;
    private int credencial;

    //relacion ternaria 
    @ManyToMany 
    @JoinTable(
        name = "dictado_clase",
        joinColumns = @JoinColumn(name = "profesor_id"), 
        inverseJoinColumns = @JoinColumn(name = "nivel_nro") 
    )
    private List<Nivel> niveles;

    @ManyToMany 
    @JoinTable(
        name = "dictado_clase",
        joinColumns = @JoinColumn(name = "profesor_id"), 
        inverseJoinColumns = @JoinColumn(name = "materia_id") 
    )
    private List<Materia> materias;

    //getters y setters 
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getFormaTrabajo() {
        return formaTrabajo;
    }
    public void setFormaTrabajo(String formaTrabajo) {
        this.formaTrabajo = formaTrabajo;
    }
    public String getInfoAcademica() {
        return infoAcademica;
    }
    public void setInfoAcademica(String infoAcademica) {
        this.infoAcademica = infoAcademica;
    }
    public int getCredencial() {
        return credencial;
    }
    public void setCredencial(int credencial) {
        this.credencial = credencial;
    }
    public List<Materia> getMaterias() {
        return materias;
    }
    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    public List<Nivel> getNiveles() {
        return niveles;
    }
    public void setNiveles(List<Nivel> niveles) {
        this.niveles = niveles;
    } 
    

}
