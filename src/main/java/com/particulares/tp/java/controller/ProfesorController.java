package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.service.ProfesorService;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/ver/{id}")
    public String verProfesor(@PathVariable Integer id, ModelMap modelo) {
        Profesor profesor = profesorService.getOne(id);
        modelo.put("profesor", profesor);
        return "alumno/verProfesor";
    }
}
