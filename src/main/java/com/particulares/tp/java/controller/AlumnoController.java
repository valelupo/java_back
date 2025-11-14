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
import com.particulares.tp.java.service.AlumnoService;
import com.particulares.tp.java.service.LocalidadService;
import com.particulares.tp.java.service.ProvinciaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personaSession");
        System.out.println("Alumno ID: " + logueado.getId());
        modelo.put("alumno", logueado);
        // modelo.put("localidades", localidadService.listarLocalidades());
        return "alumno/perfilAlumno";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable int id, ModelMap modelo) {
        modelo.put("alumno", alumnoService.getOne(id));
        modelo.put("localidades", localidadService.listarLocalidades());
        modelo.put("provincias", provinciaService.listarProvincias());
        return "alumno/modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificarAlumno(@PathVariable int id, 
                                @RequestParam String nombre, 
                                @RequestParam String apellido,
                                @RequestParam String email, 
                                @RequestParam int idLocalidad, 
                                @RequestParam String clave, 
                                @RequestParam String clave2, 
                                ModelMap modelo, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            alumnoService.modificarAlumno(nombre, apellido, email, idLocalidad, clave, clave2, id);

            Persona actualizado = alumnoService.getOne(id);
            session.setAttribute("personaSession", actualizado);

            redirectAttributes.addFlashAttribute("exito", "Perfil modificado correctamente.");
            return "redirect:/alumno/perfil";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alumno/modificar/" + id;
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            alumnoService.eliminarAlumno(id);
            redirectAttributes.addFlashAttribute("exito", "Alumno eliminada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/alumno/modificar/" + id;
        }
        return "redirect:/";
    }
}
