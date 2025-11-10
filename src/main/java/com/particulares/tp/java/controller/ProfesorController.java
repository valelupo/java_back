package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        Profesor profesor = profesorService.getOne(id);
        List<DictadoClase> dictados = dictadoClaseService.obtenerDictadosPorProfesor(id);
        Double promedio = reseniaService.promedioReseniasPorProfesor(id);
        List<Material> materiales = materialService.listarMaterialesPorProfesor(id);

        modelo.put("materiales", materiales);
        modelo.put("promedio", promedio);
        modelo.put("dictados", dictados);
        modelo.put("profesor", profesor);
        return "alumno/verProfesor";
    }
}
