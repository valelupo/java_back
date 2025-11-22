package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Materia;
import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.repository.MateriaRepository;
import com.particulares.tp.java.repository.DictadoClaseRepository;


@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Transactional
    public void crearMateria(String nombre) 
    throws Exception {

        validar(nombre);
        Materia materia = new Materia();
        materia.setNombre(nombre);
        materiaRepository.save(materia);
    }

    @Transactional(readOnly = true)
    public List<Materia> listarMaterias() {
        return materiaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Materia> listarPorNivel(int nro){
        return dictadoClaseRepository.findMateriaByNivel(nro);
    }

    @Transactional
    public void modificarMaterias(String nombre, int id) 
    throws Exception {
        
        validar(nombre);

        Optional<Materia> materiaOpt = materiaRepository.findById(id);

        if (materiaOpt.isPresent()) {
            Materia materia = materiaOpt.get();

            materia.setNombre(nombre);

            materiaRepository.save(materia);
        } else {
            throw new Exception("No se encontró una materia con el ID especificado");
        }
    }

    @Transactional
    public void eliminarMateria(int id) 
    throws Exception{

        Optional<Materia> materiaOpt = materiaRepository.findById(id);

        if (materiaOpt.isPresent()) {
            materiaRepository.delete(materiaOpt.get());
            for (DictadoClase dc : materiaOpt.get().getDictados()) {
                dictadoClaseRepository.delete(dc);
            }
            
        } else {
            throw new Exception("La materia con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Materia getOne(int id){
        return materiaRepository.getReferenceById(id);
    }

    @Transactional
    public void agregarDictado(int id, Integer idDC) 
    throws Exception{
        Materia materia = materiaRepository.findById(id).get();
        DictadoClase dictadoClase = dictadoClaseRepository.findById(idDC).get();

        if (materia == null) {
            throw new Exception("El materia especificado no existe.");
        }
        if (dictadoClase == null) {
            throw new Exception("El dictadoClase especificado no existe.");
        }

        materia.getDictados().add(dictadoClase);

        materiaRepository.save(materia);
    }


    private void validar(String nombre) 
    throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
        if (materiaRepository.findByNombre(nombre) != null){
            throw new Exception("ya existe una materia con este nombre");
        }
        
    }
    
}