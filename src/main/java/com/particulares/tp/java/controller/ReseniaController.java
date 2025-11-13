package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.service.ReseniaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resenia")
public class ReseniaController {
    
    @Autowired
    private ReseniaService reseniaService;

    @PostMapping("/registrar")
    public String registrarResenia(@RequestParam String descripcion,
                                    @RequestParam int idProfesor,
                                    @RequestParam int puntaje,
                                    HttpSession session,
                                    ModelMap model) {
        try {
            Persona alumno = (Persona) session.getAttribute("personaSession");
            reseniaService.crearResenia(descripcion, idProfesor, alumno.getId(), puntaje);

            model.put("exito", "Reseña cargada correctamente.");
        } catch (Exception e) {
            model.put("error", "Error al cargar la reseña: " + e.getMessage());
        }
        return "redirect:/profesor/ver/" + idProfesor;
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("resenias", reseniaService.listarResenias()); 
        return "profesor/verResenia";
    }

}
