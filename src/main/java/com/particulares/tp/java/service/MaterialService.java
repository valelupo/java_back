package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.repository.MaterialRepository;
import com.particulares.tp.java.repository.ProfesorRepository;


@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Transactional
    public void crearmaterial(String descripcion, int idProfesor) throws Exception {

        validar(descripcion, idProfesor);

        Profesor miProfesor = profesorRepository.findById(idProfesor).get();

        if (miProfesor == null) {
            throw new Exception("El profesor especificado no existe.");
        }

        Material material = new Material();

        material.setDescripcion(descripcion);
        material.setProfesor(miProfesor);

        materialRepository.save(material);
    }

    @Transactional(readOnly = true)
    public List<Material> listarMateriales() {

        return materialRepository.findAll();
    }

    @Transactional
    public void modificarMaterial(String descripcion, int idProfesor, int id) throws Exception {
        
        validar(descripcion, idProfesor);

        Optional<Material> materialOpt = materialRepository.findById(id);
        Optional<Profesor> profesorOpt = profesorRepository.findById(idProfesor);

        if (profesorOpt.isEmpty()) {
            throw new Exception("El profesor especificado no existe.");
        }

        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();

            material.setDescripcion(descripcion);
            material.setProfesor(profesorOpt.get());

            materialRepository.save(material);
        } else {
            throw new Exception("No se encontró un material con el id especificado");
        }
    }

    @Transactional
    public void eliminarMaterial(int id) throws Exception{
        Optional<Material> materialOpt = materialRepository.findById(id);
        if (materialOpt.isPresent()) {
            materialRepository.delete(materialOpt.get());
        } else {
            throw new Exception("El material con el id especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Material getOne(int id){
        return materialRepository.getReferenceById(id);
    }


    private void validar(String descripcion, int idProfesor) throws Exception {
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("la descripcion no puede ser nulo o estar vacío");
        }
        if (idProfesor <= 0) {
            throw new Exception("El idProfesor debe ser un numero positivo");
        }
    }
    
}