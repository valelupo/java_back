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

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.service.MateriaService;
import com.particulares.tp.java.service.NivelService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private NivelService nivelService;
    
    @GetMapping("/registrar") 
    public String registrar(ModelMap modelo, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);
        return "materia/crearMateria";
    }

    @PostMapping("/crear") 
    public String crear(@RequestParam String nombre, HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);
        try {
            materiaService.crearMateria(nombre);  
            modelo.put("exito", "La materia fue cargada correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }        
        return "materia/crearMateria";
    }

    @GetMapping("/listar")
    public String listar(@RequestParam(required = false) Integer nivel,
                        ModelMap modelo,
                        HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);
        modelo.addAttribute("niveles", nivelService.listarNiveles());

        if (nivel != null) {
            modelo.addAttribute("materias", materiaService.listarPorNivel(nivel));
        } else {
            modelo.addAttribute("materias", materiaService.listarMaterias());
        }
        modelo.addAttribute("nivelSeleccionado", nivel);
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
