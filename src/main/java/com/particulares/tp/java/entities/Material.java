package com.particulares.tp.java.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String descripcion;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] archivo;
    
    @ManyToOne
    private DictadoClase dictadoClase; 

    //getters y setters 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public byte[] getArchivo() {
        return archivo;
    }
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
    public DictadoClase getDictadoClase() {
        return dictadoClase;
    }
    public void setDictadoClase(DictadoClase dictadoClase) {
        this.dictadoClase = dictadoClase;
    }
    
}
