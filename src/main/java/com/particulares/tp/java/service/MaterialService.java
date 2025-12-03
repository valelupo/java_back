package com.particulares.tp.java.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.repository.MaterialRepository;
import com.particulares.tp.java.repository.DictadoClaseRepository;


@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private DictadoClaseRepository dictadoClaseRepository;

    @Transactional
    public void crearmaterial(String[] descripciones, int idDictadoClase,  MultipartFile[] archivos) throws Exception {

       
        DictadoClase dictadoClase = dictadoClaseRepository.findById(idDictadoClase).get();

        if (dictadoClase == null) {
            throw new Exception("El dictadoClase especificado no existe.");
        }

        for (int i = 0; i < archivos.length; i++) {
            MultipartFile archivo = archivos[i];
            String descripcion = descripciones[i];
            validar(descripcion, idDictadoClase, archivo);

            if (!archivo.isEmpty()) {
                Material material = new Material();

                material.setDescripcion(descripcion);
                material.setDictadoClase(dictadoClase);
                material.setArchivo(archivo.getBytes());

                materialRepository.save(material);
            }
        }
  
    }

    @Transactional(readOnly = true)
    public List<Material> listarMateriales() {
        return materialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Material> listarMaterialesPorProfesor(int idProfesor) {
        return materialRepository.findByProfesorId(idProfesor);
    }

    @Transactional
    public void modificarMaterial(String descripcion, int idDictadoClase, int id, MultipartFile archivo) throws Exception {
        
        validar(descripcion, idDictadoClase, archivo);

        Optional<Material> materialOpt = materialRepository.findById(id);
        Optional<DictadoClase> dictadoClaseOpt = dictadoClaseRepository.findById(idDictadoClase);

        if (dictadoClaseOpt.isEmpty()) {
            throw new Exception("El dictadoClase especificado no existe.");
        }

        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();

            material.setDescripcion(descripcion);
            material.setDictadoClase(dictadoClaseOpt.get());
            if (archivo != null && !archivo.isEmpty()) {
                material.setArchivo(archivo.getBytes());
            }

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


    private void validar(String descripcion, int idDictadoClase, MultipartFile archivo) throws Exception {
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("la descripcion no puede ser nulo o estar vacío");
        }
        if (idDictadoClase <= 0) {
            throw new Exception("El idDictadoClase debe ser un numero positivo");
        }
        if (archivo == null && archivo.isEmpty()) {
            throw new Exception("Debe proporcionar un archivo");
        }
    }
    
}