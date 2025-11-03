package com.particulares.tp.java.entities;

import java.util.List;

import com.particulares.tp.java.enums.FormaTrabajo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor extends Persona{
    private String telefono;
    @Enumerated(EnumType.STRING)
    private FormaTrabajo formaTrabajo;
    private String infoAcademica;
    private double precioXHs;

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
    public void setDictados(List<DictadoClase> dictados) {
        this.dictados = dictados;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    public double getPrecioXHs() {
        return precioXHs;
    }
    public void setPrecioXHs(double precioXHs) {
        this.precioXHs = precioXHs;
    }
    public FormaTrabajo getFormaTrabajo() {
        return formaTrabajo;
    }
    public void setFormaTrabajo(FormaTrabajo formaTrabajo) {
        this.formaTrabajo = formaTrabajo;
    } 
    

}
