package com.particulares.tp.java.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Resenia;
import com.particulares.tp.java.repository.ReseniaRepository;


@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Transactional
    public void crearResenia(String descripcion) throws Exception {

        validar(descripcion);

        Resenia resenia = new Resenia();

        resenia.setDescripcion(descripcion);
        resenia.setFecha(LocalDateTime.now());

        reseniaRepository.save(resenia);
    }

    @Transactional(readOnly = true)
    public List<Resenia> listarNiveles() {

        return reseniaRepository.findAll();
    }

    @Transactional
    public void modificarResenia(String descripcion, int id) throws Exception {
        
        validar(descripcion);

        Optional<Resenia> respuesta = reseniaRepository.findById(id);

        if (respuesta.isPresent()) {
            Resenia resenia = respuesta.get();

            resenia.setDescripcion(descripcion);

            reseniaRepository.save(resenia);
        } else {
            throw new Exception("No se encontró un resenia con el ID especificado");
        }
    }

    @Transactional
    public void eliminar(int id) throws Exception{
        Optional<Resenia> reseniaOpt = reseniaRepository.findById(id);
        if (reseniaOpt.isPresent()) {
            reseniaRepository.delete(reseniaOpt.get());
        } else {
            throw new Exception("El resenia con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Resenia  getOne(int id){
        return reseniaRepository.getReferenceById(id);
    }


    private void validar(String descripcion) throws Exception {
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("la descripcion no puede ser nulo o estar vacío");
        }
    }
    
}