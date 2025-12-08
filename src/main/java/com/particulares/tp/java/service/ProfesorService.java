package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.entities.Resenia;
import com.particulares.tp.java.enums.FormaTrabajo;
import com.particulares.tp.java.enums.Rol;
import com.particulares.tp.java.dto.ProfesorConPuntajeDTO;
import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.repository.ProfesorRepository;
import com.particulares.tp.java.repository.ReseniaRepository;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.repository.MaterialRepository;
import com.particulares.tp.java.repository.PersonaRepository;
import com.particulares.tp.java.repository.DictadoClaseRepository;


@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public void crearProfesor(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2,
                            String telefono, String formaTrabajo, String infoAcademica, Double precioXHs, MultipartFile imagen) throws Exception {

        validar(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, precioXHs, imagen);
        Optional<Persona> existente = personaRepository.findByEmail(email);
        if (existente.isPresent()) {
            throw new Exception("Ya existe una cuenta con ese email.");
        }
        Localidad miLocalidad = localidadRepository.findById(idLocalidad).get();
        FormaTrabajo estadoFT = FormaTrabajo.valueOf(formaTrabajo);

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
        profesor.setFormaTrabajo(estadoFT);
        profesor.setInfoAcademica(infoAcademica);
        profesor.setPrecioXHs(precioXHs);
        profesor.setImagen(imagen.getBytes());
        profesorRepository.save(profesor);
    }

    @Transactional(readOnly = true)
    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProfesorConPuntajeDTO> listarProfesoresPorPuntaje() {
        List<ProfesorConPuntajeDTO> profesoresDTO = profesorRepository.profesoresConPuntaje();
        return cargarDictados(profesoresDTO);
    }

    @Transactional(readOnly = true)
    public List<ProfesorConPuntajeDTO> listarProfesoresPorPuntajeOrdenados() {
        List<ProfesorConPuntajeDTO> profesoresDTO = profesorRepository.profesoresConPuntajeOrdenados();
        return cargarDictados(profesoresDTO);
    }
    
    @Transactional(readOnly = true)
    public List<ProfesorConPuntajeDTO> buscarPorMateria (String materia){
        List<ProfesorConPuntajeDTO> profesoresDTO = profesorRepository.profesoresPorMateria(materia.toLowerCase());
        return cargarDictados(profesoresDTO);
    }

    @Transactional(readOnly = true)
    public List<ProfesorConPuntajeDTO> buscarPorMateriaOrdenado (String materia){
        List<ProfesorConPuntajeDTO> profesoresDTO = profesorRepository.profesoresPorMateriaOrdenados(materia.toLowerCase());
        return cargarDictados(profesoresDTO);
    }

    public List<ProfesorConPuntajeDTO> cargarDictados(List<ProfesorConPuntajeDTO> profesoresDTO){
        for (ProfesorConPuntajeDTO pdto : profesoresDTO) {
            List<DictadoClase> dictados = dictadoClaseRepository.findByProfesor(pdto.getId());
            pdto.setDictados(dictados);
        }
        return profesoresDTO;
    }

    @Transactional
    public Profesor modificarProfesor(String nombre, String apellido, String email, int idLocalidad, String clave, String clave2,
                                  String telefono, String formaTrabajo, String infoAcademica, Double precioXHs, int id, MultipartFile imagen) throws Exception {
        
        validar(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, precioXHs, imagen);
        Optional<Persona> existente = personaRepository.findByEmail(email);
        if (existente.isPresent() && existente.get().getId() != id) {
            throw new Exception("Ya existe una cuenta con ese email.");
        }

        Optional<Localidad> localidadOpt = localidadRepository.findById(idLocalidad);
        Optional<Profesor> profesorOpt = profesorRepository.findById(id);
        FormaTrabajo estadoFT = FormaTrabajo.valueOf(formaTrabajo);

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
            profesor.setFormaTrabajo(estadoFT);
            profesor.setInfoAcademica(infoAcademica);
            profesor.setPrecioXHs(precioXHs);
           
           if (imagen != null && !imagen.isEmpty()) {
                profesor.setImagen(imagen.getBytes());
           }

            return profesorRepository.save(profesor);
        } else {
            throw new Exception("No se encontró un profesor con el ID especificado");
        }
    }

    @Transactional
    public void eliminarProfesor(int id) throws Exception {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new Exception("El profesor con el ID especificado no existe"));

        profesorRepository.delete(profesor);
    }

    // @Transactional
    // public void eliminarProfesor(int id) throws Exception{
    //     Optional<Profesor> profesorOpt = profesorRepository.findById(id);
    //     if (profesorOpt.isPresent()) {
    //         for (Material m : materialRepository.findByProfesorId(id)) {
    //             materialRepository.delete(m);
    //         }
    //         for (DictadoClase dc : profesorOpt.get().getDictados()) {
    //             dictadoClaseRepository.delete(dc);
    //         }
    //         List<Resenia> resenias = reseniaRepository.findReseniasByProfesorId(id);
    //         for (Resenia r: resenias){
    //             reseniaRepository.delete(r);
    //         }
    //         profesorRepository.delete(profesorOpt.get());
    //     } else {
    //         throw new Exception("El profesor con el ID especificado no existe");
    //     }
    // }

    @Transactional(readOnly = true)
    public Profesor getOne(int id){
        return profesorRepository.getReferenceById(id);
    }

    public void agregarDictado(int id, Integer idDC) 
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
                         String telefono, String formaTrabajo, String infoAcademica, double precioXHs, MultipartFile imagen) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new Exception("el apellido no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("el email no puede ser nulo o estar vacío");
        }

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
        if (precioXHs <= 0) {
            throw new Exception("el precioXHs debe ser un numero positivo");
        }
        if (imagen == null && imagen.isEmpty()) {
            throw new Exception("Debe proporcionar una imagen.");
        }

    }
    
}