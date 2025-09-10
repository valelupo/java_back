package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.repository.ProvinciaRepository;


@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void crearProvincia(String nombre) throws Exception {

        validar(nombre);

        Provincia provincia = new Provincia();

        provincia.setNombre(nombre);

        provinciaRepository.save(provincia);
    }

    @Transactional(readOnly = true)
    public List<Provincia> listarProvincias() {

        return provinciaRepository.findAll();
    }

    @Transactional
    public void modificarProvincia(String nombre, int id) throws Exception {
        
        validar(nombre);

        Optional<Provincia> provOpt = provinciaRepository.findById(id);

        if (provOpt.isPresent()) {
            Provincia provincia = provOpt.get();

            provincia.setNombre(nombre);

            provinciaRepository.save(provincia);
        } else {
            throw new Exception("No se encontró una provincia con el id especificado");
        }
    }

    @Transactional
    public void eliminarProvincia(int id) throws Exception{
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(id);
        if (provinciaOpt.isPresent()) {
            provinciaRepository.delete(provinciaOpt.get());
        } else {
            throw new Exception("La provincia con el id especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Provincia getOne(int id){
        return provinciaRepository.getReferenceById(id);
    }


    private void validar(String nombre) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
    }
    
}