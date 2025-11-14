package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.service.DictadoClaseService;
import com.particulares.tp.java.service.MateriaService;
import com.particulares.tp.java.service.NivelService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dictadoClase")
public class DictadoClaseController {

    @Autowired
    private DictadoClaseService dictadoClaseService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private NivelService nivelService;
    
    @GetMapping("/registrar") 
    public String registrar(ModelMap modelo) {
            modelo.addAttribute("materias", materiaService.listarMaterias());
            modelo.addAttribute("niveles", nivelService.listarNiveles());
        return "profesor/crearDictado";
    }

    @PostMapping("/crear") 
    public String crear(HttpSession session, 
                        @RequestParam int idMateria, 
                        @RequestParam(name = "nrosNiveles", required = false) List<Integer> nrosNiveles, 
                        ModelMap modelo){
        try {
            Profesor profesor = (Profesor) session.getAttribute("personaSession");
            dictadoClaseService.crearDictadoClase(profesor.getId(), idMateria, nrosNiveles);   
            modelo.put("exito", "El dictado fue cargado correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

        }        
        return "profesor/crearDictado";
    }

    @GetMapping("/lista") 
    public String listar(ModelMap modelo, HttpSession session) {

        Profesor profesor = (Profesor) session.getAttribute("personaSession");
        List<DictadoClase> dictados = dictadoClaseService.obtenerDictadosPorProfesor(profesor.getId());
        if (dictados.isEmpty()) {
            modelo.addAttribute("mensaje", "No hay dictados asignados actualmente.");
        } else {
            modelo.addAttribute("dictados", dictados);
        }

        return "profesor/verDictados";
    }

    @PostMapping("/eliminar/{idDC}")
    public String eliminar(@PathVariable int idDC, RedirectAttributes redirectAttributes) {
        try {
            dictadoClaseService.eliminarDictado(idDC);
            redirectAttributes.addFlashAttribute("exito", "Dictado eliminado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/dictadoClase/lista";
    }



}
