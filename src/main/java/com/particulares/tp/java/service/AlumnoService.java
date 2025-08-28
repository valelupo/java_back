package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Alumno;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.repository.AlumnoRepository;
import com.particulares.tp.java.repository.LocalidadRepository;


@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional
    public void crearAlumno(String nombre, String apellido, String email, int idLocalidad) throws Exception {

        validar(nombre, apellido, email, idLocalidad);

        Localidad miLocalidad = localidadRepository.findById(idLocalidad).get();

        if (miLocalidad == null) {
            throw new Exception("La localidad especificada no existe.");
        }

        Alumno alumno = new Alumno();

        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEmail(email);
        alumno.setMiLocalidad(miLocalidad);

        alumnoRepository.save(alumno);
    }

    @Transactional(readOnly = true)
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    @Transactional
    public void modificarAlumno(String nombre, String apellido, String email, int idLocalidad, int id) throws Exception {
        
        validar(nombre, apellido, email, idLocalidad);

        Optional<Localidad> localidadOpt = localidadRepository.findById(idLocalidad);
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);

        if (localidadOpt.isEmpty()) {
            throw new Exception("La localidad especificada no existe.");
        }

        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();

            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setEmail(email);
            alumno.setMiLocalidad(localidadOpt.get());

            alumnoRepository.save(alumno);
        } else {
            throw new Exception("No se encontró un alumno con el ID especificado");
        }
    }

    @Transactional
    public void eliminar(int id) throws Exception{
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            alumnoRepository.delete(alumnoOpt.get());
        } else {
            throw new Exception("El alumno con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Alumno getOne(int id){
        return alumnoRepository.getReferenceById(id);
    }


    private void validar(String nombre, String apellido, String email, int idLocalidad) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new Exception("el apellido no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("el email no puede ser nulo o estar vacío");
        }
        // if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        //     throw new Exception("El email no tiene un formato válido");
        // }
        if(idLocalidad <= 0) {
            throw new Exception("El idAutor debe ser un numero positivo");
        }

    }
    
}