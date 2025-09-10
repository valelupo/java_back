package com.particulares.tp.java.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor extends Persona{
    private String telefono;
    private String formaTrabajo;
    private String infoAcademica;
    private int matricula; // o es matricula?

    @OneToMany(mappedBy = "profesor") //se usa mappedBy para indicar que la relacion se mapea a traves de otra entidad 
    private List<DictadoClase> dictados;

/*    //relacion ternaria 
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
*/ 

    //getters y setters 
    public String getTelefono() {
        return telefono;
    }
    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public void setDictados(List<DictadoClase> dictados) {
        this.dictados = dictados;
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
    
    public List<DictadoClase> getDictados() {
        return dictados;
    } 
    

}
