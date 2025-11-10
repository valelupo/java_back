package com.particulares.tp.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.Localidad;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer>{
    
    @Query("SELECT l FROM Localidad l WHERE l.miProvincia.id = ?1")
    List<Localidad> buscarPorProvinciaId(int idProv);

    @Query("SELECT l FROM Localidad l " + "WHERE LOWER(TRIM(l.miProvincia.nombre)) = LOWER(TRIM(:provincia))")
    List<Localidad> buscarPorProvinciaNombre(String provincia);

    @Query("SELECT l FROM Localidad l WHERE l.nombre = :nombre OR l.codPostal = :codPostal")
    Localidad findByNombreOrCodigoPostal(@Param("nombre") String nombre, @Param("codPostal") Integer codPostal);
} 