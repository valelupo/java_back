package com.particulares.tp.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Materia;
import com.particulares.tp.java.entities.Nivel;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.DictadoClaseId;
import com.particulares.tp.java.repository.NivelRepository;
import com.particulares.tp.java.repository.MateriaRepository;
import com.particulares.tp.java.repository.ProfesorRepository;
import com.particulares.tp.java.repository.DictadoClaseRepository;

@Service
public class DictadoClaseService {
    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private NivelRepository nivelRepository;


    @Transactional
    public void crearDictadoClase(int idProfesor, int idMateria, int nroNivel) 
    throws Exception {

        validar(idProfesor, idMateria, nroNivel);

        Profesor miProfesor = profesorRepository.findById(idProfesor).get();
        Materia miMateria = materiaRepository.findById(idMateria).get();
        Nivel miNivel = nivelRepository.findById(nroNivel).get();

        if (miProfesor == null) {
            throw new Exception("El profesor especificado no existe.");
        }

        if (miMateria == null) {
            throw new Exception("La meteria especificada no existe.");
        }

        if (miNivel == null) {
            throw new Exception("El nivel especificado no existe.");
        }


        DictadoClase dictadoClase = new DictadoClase();

        dictadoClase.setProfesor(miProfesor);
        dictadoClase.setMateria(miMateria);
        dictadoClase.setNivel(miNivel);

        dictadoClaseRepository.save(dictadoClase);
    }

    @Transactional(readOnly = true)
    public List<DictadoClase> listarDictadoClases() {

        return dictadoClaseRepository.findAll();
    }

    @Transactional
    public void modificarDictados(int idProfesor, int idMateria, int nroNivel, DictadoClaseId idDC) 
    throws Exception {
        
        validar(idProfesor, idMateria, nroNivel);

        Optional<DictadoClase> dictadoClaseOpt = dictadoClaseRepository.findById(idDC);
        Optional<Profesor> profesorOpt = profesorRepository.findById(idProfesor);
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        Optional<Nivel> nivelOpt = nivelRepository.findById(nroNivel);

        if (profesorOpt.isEmpty()) {
            throw new Exception("El profesor especificado no existe.");
        }
        if (materiaOpt.isEmpty()) {
            throw new Exception("La materia especificada no existe.");
        }
        if (nivelOpt.isEmpty()) {
            throw new Exception("El nivel especificado no existe.");
        }

        if (dictadoClaseOpt.isPresent()) {
            DictadoClase dictadoClase = dictadoClaseOpt.get();

            dictadoClase.setMateria(materiaOpt.get());
            dictadoClase.setProfesor(profesorOpt.get());
            dictadoClase.setNivel(nivelOpt.get());

            dictadoClaseRepository.save(dictadoClase);
        } else {
            throw new Exception("No se encontró un dictadoClase con el ID especificado");
        }
    }

    @Transactional
    public void eliminarDictado(DictadoClaseId idDC) 
    throws Exception{
        Optional<DictadoClase> dictadoClaseOpt = dictadoClaseRepository.findById(idDC);
        if (dictadoClaseOpt.isPresent()) {
            dictadoClaseRepository.delete(dictadoClaseOpt.get());
        } else {
            throw new Exception("El dictadoClase con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public DictadoClase  getOne(DictadoClaseId idDC){
        return dictadoClaseRepository.getReferenceById(idDC);
    }


    private void validar(int idProfesor, int idMateria, int nroNivel) 
    throws Exception {
        if (idMateria <= 0) {
            throw new Exception("El idMateria debe ser un numero positivo");
        }
        if (idProfesor <= 0) {
            throw new Exception("El idProfesor debe ser un numero positivo");
        }
        if (nroNivel <= 0) {
            throw new Exception("El nroNivel debe ser un numero positivo");
        }
    }
    
}    

