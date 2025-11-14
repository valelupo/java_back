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
import com.particulares.tp.java.service.NivelService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/nivel")
public class NivelController {
    
    @Autowired
    private NivelService nivelService;
    
    @GetMapping("/registrar") 
    public String registrar(HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);
        return "nivel/crearNivel";
    }

    @PostMapping("/crear") 
    public String crear(@RequestParam String descripcion, ModelMap modelo, HttpSession session){
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);
        try {
            nivelService.crearNivel(descripcion);  
            modelo.put("exito", "El nivel fue cargado correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }        
        return "nivel/crearNivel";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("niveles", nivelService.listarNiveles()); 
        return "admin/nivel/lista";
    }

    @PostMapping("/eliminar/{nro}")
    public String eliminar(@PathVariable int nro, RedirectAttributes redirectAttributes) {
        try {
            nivelService.eliminar(nro);
            redirectAttributes.addFlashAttribute("exito", "Nivel eliminado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/nivel/listar";
    } 
}

