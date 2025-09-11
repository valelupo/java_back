package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.enums.Rol;
import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.DictadoClaseId;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.repository.ProfesorRepository;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.repository.DictadoClaseRepository;


@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Transactional
    public void crearProfesor(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2,
                              String telefono, String formaTrabajo, String infoAcademica, Integer matricula) throws Exception {

        validar(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, matricula);

        Localidad miLocalidad = localidadRepository.findById(idLocalidad).get();

        if (miLocalidad == null) {
            throw new Exception("La localidad especificada no existe.");
        }

        Profesor profesor = new Profesor();

        profesor.setNombre(nombre);
        profesor.setApellido(apellido);
        profesor.setEmail(email);
        profesor.setMiLocalidad(miLocalidad);
        profesor.setClave(new BCryptPasswordEncoder().encode(clave)); 
        profesor.setRol(Rol.PROFESOR);
        profesor.setTelefono(telefono);
        profesor.setFormaTrabajo(formaTrabajo);
        profesor.setInfoAcademica(infoAcademica);
        profesor.setMatricula(matricula);

        profesorRepository.save(profesor);
    }

    @Transactional(readOnly = true)
    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    @Transactional
    public void modificarProfesor(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2,
                                  String telefono, String formaTrabajo, String infoAcademica, Integer matricula, int id) throws Exception {
        
        validar(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, matricula);

        Optional<Localidad> localidadOpt = localidadRepository.findById(idLocalidad);
        Optional<Profesor> profesorOpt = profesorRepository.findById(id);

        if (localidadOpt.isEmpty()) {
            throw new Exception("La localidad especificada no existe.");
        }

        if (profesorOpt.isPresent()) {
            Profesor profesor = profesorOpt.get();

            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            profesor.setEmail(email);
            profesor.setMiLocalidad(localidadOpt.get());
            profesor.setClave(new BCryptPasswordEncoder().encode(clave)); 
            profesor.setRol(Rol.PROFESOR);
            profesor.setTelefono(telefono);
            profesor.setFormaTrabajo(formaTrabajo);
            profesor.setInfoAcademica(infoAcademica);
            profesor.setMatricula(matricula);

            profesorRepository.save(profesor);
        } else {
            throw new Exception("No se encontró un profesor con el ID especificado");
        }
    }

    @Transactional
    public void eliminarProfesor(int id) throws Exception{
        Optional<Profesor> profesorOpt = profesorRepository.findById(id);
        if (profesorOpt.isPresent()) {
            profesorRepository.delete(profesorOpt.get());
        } else {
            throw new Exception("El profesor con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Profesor getOne(int id){
        return profesorRepository.getReferenceById(id);
    }

    public void agregarDictado(int id, DictadoClaseId idDC) 
    throws Exception{
        Profesor profesor = profesorRepository.findById(id).get();
        DictadoClase dictadoClase = dictadoClaseRepository.findById(idDC).get();

        if (profesor == null) {
            throw new Exception("El profesor especificado no existe.");
        }
        if (dictadoClase == null) {
            throw new Exception("El dictadoClase especificado no existe.");
        }

        profesor.getDictados().add(dictadoClase);

        profesorRepository.save(profesor);
    }


    private void validar(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2,
                         String telefono, String formaTrabajo, String infoAcademica, Integer matricula) throws Exception {
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
        if (telefono.isEmpty() || telefono == null) {
            throw new Exception("el telefono no puede ser nulo o estar vacío");
        }
        if (formaTrabajo.isEmpty() || formaTrabajo == null) {
            throw new Exception("la formaTrabajo no puede ser nulo o estar vacío");
        }
        if (infoAcademica.isEmpty() || infoAcademica == null) {
            throw new Exception("la infoAcademica no puede ser nulo o estar vacío");
        }
        if (matricula == null || matricula == 0) {
            throw new Exception("el matricula no puede ser nula o 0");
        }
    }
    
}