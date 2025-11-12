package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.service.DictadoClaseService;
import com.particulares.tp.java.service.MaterialService;
import com.particulares.tp.java.service.ProfesorService;
import com.particulares.tp.java.service.ReseniaService;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private DictadoClaseService dictadoClaseService;

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/ver/{id}")
    public String verProfesor(@PathVariable Integer id, ModelMap modelo) {
        //modelo.put("materiales", materialService.listarMaterialesPorProfesor(id););
        modelo.put("promedio", reseniaService.promedioReseniasPorProfesor(id));
        modelo.put("dictados", dictadoClaseService.obtenerDictadosPorProfesor(id));
        modelo.put("profesor", profesorService.getOne(id));
        // modelo.put("resenias", reseniaService.);
        return "alumno/verProfesor";
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenProfesor(@PathVariable int id) {
        Profesor profesor = profesorService.getOne(id); 

        if (profesor.getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
            return new ResponseEntity<>(profesor.getImagen(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
