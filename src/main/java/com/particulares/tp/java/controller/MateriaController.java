package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.particulares.tp.java.service.MateriaService;

@Controller
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;
    
    @GetMapping("/registrar") 
    public String registrar() {
        return "materia/crearMateria";
    }

    @PostMapping("/crear") 
    public String crear(@RequestParam String nombre, ModelMap modelo){
        try {
            materiaService.crearMateria(nombre);  
            modelo.put("exito", "La materia fue cargada correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }        
        return "materia/crearMateria";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("materias", materiaService.listarMaterias()); 
        return "admin/materia/lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            materiaService.eliminarMateria(id);
            redirectAttributes.addFlashAttribute("exito", "Materia eliminada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/materia/listar";
    } 
}
