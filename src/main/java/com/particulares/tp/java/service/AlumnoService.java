package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Alumno;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.enums.Rol;
import com.particulares.tp.java.repository.AlumnoRepository;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.repository.PersonaRepository;


@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public void crearAlumno(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2) throws Exception {

        validar(nombre, apellido, email, idLocalidad, clave, clave2);

        Optional<Persona> existente = personaRepository.findByEmail(email);
        if (existente.isPresent()) {
            throw new Exception("Ya existe una cuenta con ese email.");
        }

        Localidad miLocalidad = localidadRepository.findById(idLocalidad).get();

        if (miLocalidad == null) {
            throw new Exception("La localidad especificada no existe.");
        }

        Alumno alumno = new Alumno();

        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEmail(email);
        alumno.setMiLocalidad(miLocalidad);
        alumno.setClave(new BCryptPasswordEncoder().encode(clave)); // para guardar la clve encriptada
        alumno.setRol(Rol.ALUMNO);

        alumnoRepository.save(alumno);
    }

    @Transactional(readOnly = true)
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    @Transactional
    public void modificarAlumno(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2, int id) throws Exception {
        
        validar(nombre, apellido, email, idLocalidad, clave, clave2);
        Optional<Persona> existente = personaRepository.findByEmail(email);
        if (existente.isPresent() && existente.get().getId() != id) {
            throw new Exception("Ya existe una cuenta con ese email.");
        }

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
            alumno.setClave(new BCryptPasswordEncoder().encode(clave));
            alumno.setRol(Rol.ALUMNO);

            alumnoRepository.save(alumno);
        } else {
            throw new Exception("No se encontró un alumno con el ID especificado");
        }
    }

    @Transactional
    public void eliminarAlumno(int id) throws Exception{
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


    private void validar(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2) throws Exception {
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
            throw new Exception("El idLocalidad debe ser un numero positivo");
        }
        if (clave.isEmpty() || clave == null || clave.length() <= 5) {
            throw new Exception("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!clave.equals(clave2)) {
            throw new Exception("Las contraseñas ingresadas deben ser iguales");
        }

    }
    
}