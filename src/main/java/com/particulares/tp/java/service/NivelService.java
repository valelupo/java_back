package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.DictadoClaseId;
import com.particulares.tp.java.entities.Nivel;
import com.particulares.tp.java.repository.DictadoClaseRepository;
import com.particulares.tp.java.repository.NivelRepository;


@Service
public class NivelService {
    @Autowired
    private NivelRepository nivelRepository;
    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Transactional
    public void crearNivel(String descripcion) throws Exception {

        validar(descripcion);

        Nivel nivel = new Nivel();

        nivel.setDescripcion(descripcion);

        nivelRepository.save(nivel);
    }

    @Transactional(readOnly = true)
    public List<Nivel> listarNiveles() {

        return nivelRepository.findAll();
    }

    @Transactional
    public void modificarNivel(String descripcion, int nro) throws Exception {
        
        validar(descripcion);

        Optional<Nivel> respuesta = nivelRepository.findById(nro);

        if (respuesta.isPresent()) {
            Nivel nivel = respuesta.get();

            nivel.setDescripcion(descripcion);

            nivelRepository.save(nivel);
        } else {
            throw new Exception("No se encontró un nivel con el numero especificado");
        }
    }

    @Transactional
    public void eliminar(int nro) throws Exception{
        Optional<Nivel> nivelOpt = nivelRepository.findById(nro);
        if (nivelOpt.isPresent()) {
            nivelRepository.delete(nivelOpt.get());
        } else {
            throw new Exception("El nivel con el numero especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Nivel  getOne(int nro){
        return nivelRepository.getReferenceById(nro);
    }

    public void agregarDictado(int nro, DictadoClaseId idDC) 
    throws Exception{
        Nivel nivel = nivelRepository.findById(nro).get();
        DictadoClase dictadoClase = dictadoClaseRepository.findById(idDC).get();

        if (nivel == null) {
            throw new Exception("El nivel especificado no existe.");
        }
        if (dictadoClase == null) {
            throw new Exception("El dictadoClase especificado no existe.");
        }

        nivel.getDictados().add(dictadoClase);

        nivelRepository.save(nivel);
    }


    private void validar(String descripcion) throws Exception {
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("la descripcion no puede ser nulo o estar vacío");
        }
    }
    
}