package com.particulares.tp.java.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Alumno;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.entities.Resenia;
import com.particulares.tp.java.repository.AlumnoRepository;
import com.particulares.tp.java.repository.ProfesorRepository;
import com.particulares.tp.java.repository.ReseniaRepository;


@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Transactional
    public void crearResenia(String descripcion, int idProfesor, int idAlumno, int puntaje) throws Exception {

        validar(descripcion, idAlumno, idProfesor, puntaje);

        Profesor miProfesor = profesorRepository.findById(idProfesor).get();
        Alumno miAlumno = alumnoRepository.findById(idAlumno).get();

        if (miProfesor == null) {
            throw new Exception("El profesor especificado no existe.");
        }

        if (miAlumno == null) {
            throw new Exception("El alumno especificado no existe.");
        }

        Resenia resenia = new Resenia();

        resenia.setDescripcion(descripcion);
        resenia.setFecha(LocalDateTime.now());
        resenia.setAlumno(miAlumno);
        resenia.setProfesor(miProfesor);
        resenia.setPuntaje(puntaje);

        reseniaRepository.save(resenia);
    }

    @Transactional(readOnly = true)
    public List<Resenia> listarResenias() {
        return reseniaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Resenia> listarPorProfesor(int idProfesor){
        return reseniaRepository.findByProfesor(idProfesor);
    }

    @Transactional(readOnly = true)
    public Double promedioReseniasPorProfesor(int idProfesor) {
        Double promedio = reseniaRepository.findPromedioByProfesorId(idProfesor);
        return promedio != null ? promedio : 0.0;
    }

    @Transactional(readOnly = true)
    public List<Resenia> reseniasPorProfesor(int idProfesor) {
        return reseniaRepository.findReseniasByProfesorId(idProfesor);
    }

    @Transactional
    public void modificarResenia(String descripcion, int idProfesor, int idAlumno, Integer idResenia, int puntaje) throws Exception {
        
        validar(descripcion, idAlumno, idProfesor, puntaje);

        Optional<Resenia> reseniaOpt = reseniaRepository.findById(idResenia);
        Optional<Profesor> profesorOpt = profesorRepository.findById(idProfesor);
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(idAlumno);

        if (profesorOpt.isEmpty()) {
            throw new Exception("El profesor especificado no existe.");
        }
        if (alumnoOpt.isEmpty()) {
            throw new Exception("El alumno especificado no existe.");
        }

        if (reseniaOpt.isPresent()) {
            Resenia resenia = reseniaOpt.get();

            resenia.setDescripcion(descripcion);
            resenia.setFecha(LocalDateTime.now()); // la fecha se actualiza al modificar
            resenia.setAlumno(alumnoOpt.get());
            resenia.setProfesor(profesorOpt.get());
            resenia.setPuntaje(puntaje);

            reseniaRepository.save(resenia);
        } else {
            throw new Exception("No se encontró un resenia con el ID especificado");
        }
    }

    @Transactional
    public void eliminarResenia(Integer idResenia) throws Exception{
        Optional<Resenia> reseniaOpt = reseniaRepository.findById(idResenia);
        if (reseniaOpt.isPresent()) {
            reseniaRepository.delete(reseniaOpt.get());
        } else {
            throw new Exception("El resenia con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Resenia  getOne(Integer idResenia){
        return reseniaRepository.getReferenceById(idResenia);
    }


    private void validar(String descripcion, int idAlumno, int idProfesor, int puntaje) throws Exception {
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("la descripcion no puede ser nulo o estar vacío");
        }
        if (idAlumno <= 0) {
            throw new Exception("El idAlumno debe ser un numero positivo");
        }
        if (idProfesor <= 0) {
            throw new Exception("El idProfesor debe ser un numero positivo");
        }
        if (puntaje < 1 || puntaje > 5) {
            throw new Exception("El puntaje debe estar entre 1 y 5");
        }
    }
    
}