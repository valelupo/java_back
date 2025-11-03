package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.particulares.tp.java.service.DictadoClaseService;

@Controller
@RequestMapping("/dictadoClase")
public class DictadoClaseController {

    @Autowired
    private DictadoClaseService dictadoClaseService;
    
    @GetMapping("/registrar") 
    public String registrar() {
        return "profesor/crearDictado";
    }

    @PostMapping("/crear") // localhost:8080/dictadoClase/crear
    public String crear(@RequestParam int idProfesor, int idMateria, int nroNivel, ModelMap modelo){
        try {
            dictadoClaseService.crearDictadoClase(idProfesor, idMateria, nroNivel);   
            modelo.put("exito", "El dictado fue cargado correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

        }        
        return "profesor/crearDictado";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("dictados", dictadoClaseService.listarDictadoClases()); 

        return "profesor/verDictados";
    }

    @PostMapping("/eliminar")
    public String eliminar(@PathVariable int idDC, ModelMap modelo) {
        try {
            dictadoClaseService.eliminarDictado(idDC);           
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        return "profesor/verDictados";
    }
}
