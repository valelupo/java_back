package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.repository.ProvinciaRepository;


@Service
public class LocalidadService {
    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void crearLocalidad(String nombre, int idProvincia) throws Exception {

        validar(nombre, idProvincia);

        Provincia miProvincia = provinciaRepository.findById(idProvincia).get();

        if (miProvincia == null) {
            throw new Exception("La provincia especificada no existe.");
        }

        Localidad localidad = new Localidad();

        localidad.setNombre(nombre);
        localidad.setMiProvincia(miProvincia);

        localidadRepository.save(localidad);
    }

    @Transactional(readOnly = true)
    public List<Localidad> listarLocalidades() {

        return localidadRepository.findAll();
    }

    @Transactional
    public void modificarLocalidad(String nombre,int idProvincia, int cod) throws Exception {
        
        validar(nombre, idProvincia);

        Optional<Provincia> provOpt = provinciaRepository.findById(idProvincia);
        Optional<Localidad> locOpt = localidadRepository.findById(cod);

         if (provOpt.isEmpty()) {
            throw new Exception("La provincia especificada no existe.");
        }

        if (locOpt.isPresent()) {
            Localidad localidad = locOpt.get();

            localidad.setNombre(nombre);
            localidad.setMiProvincia(provOpt.get());

            localidadRepository.save(localidad);
        } else {
            throw new Exception("No se encontró una localidad con el numero especificado");
        }
    }

    @Transactional
    public void eliminarLocalidad(int cod) throws Exception{
        Optional<Localidad> localidadOpt = localidadRepository.findById(cod);
        if (localidadOpt.isPresent()) {
            localidadRepository.delete(localidadOpt.get());
        } else {
            throw new Exception("La localidad con el numero especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Localidad getOne(int cod){
        return localidadRepository.getReferenceById(cod);
    }


    private void validar(String nombre, int idProvincia) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
        if(idProvincia <= 0) {
            throw new Exception("El idProvincia debe ser un numero positivo");
        }
    }
    
}