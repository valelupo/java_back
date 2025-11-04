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
    public void crearDictadoClase(int idProfesor, int idMateria, List<Integer> nrosNiveles) 
    throws Exception {
        for(Integer nroNivel: nrosNiveles){
            validar(idProfesor, idMateria, nroNivel);

            DictadoClase existente = dictadoClaseRepository.findByMateriaProfesorNivel(idProfesor, idMateria, nroNivel);

            if (existente != null) {
                throw new Exception("Ya tienes un dictado con la mteria y nivel seleccionado");
            }

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
    }

    @Transactional(readOnly = true)
    public List<DictadoClase> listarDictadoClases() {

        return dictadoClaseRepository.findAll();
    }


    @Transactional
    public void eliminarDictado(Integer idDC) 
    throws Exception{
        Optional<DictadoClase> dictadoClaseOpt = dictadoClaseRepository.findById(idDC);
        if (dictadoClaseOpt.isPresent()) {
            dictadoClaseRepository.delete(dictadoClaseOpt.get());
        } else {
            throw new Exception("El dictadoClase con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public DictadoClase  getOne(Integer idDC){
        return dictadoClaseRepository.getReferenceById(idDC);
    }

    @Transactional(readOnly = true)
    public List<DictadoClase> obtenerDictadosPorProfesor(Integer idProfesor){
        return dictadoClaseRepository.findByProfesor(idProfesor);
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

