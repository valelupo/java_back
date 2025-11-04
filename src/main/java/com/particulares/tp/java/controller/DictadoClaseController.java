package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registrar() {
        return "profesor/crearDictado";
    }

    @PostMapping("/crear") // localhost:8080/dictadoClase/crear
    public String crear(@RequestParam int idProfesor, int idMateria, List<Integer> nrosNiveles, ModelMap modelo){
        try {
            dictadoClaseService.crearDictadoClase(idProfesor, idMateria, nrosNiveles);   
            modelo.put("exito", "El dictado fue cargado correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

        }        
        return "profesor/crearDictado";
    }

    @GetMapping("/lista") //dictados 
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

    @PostMapping("/eliminar")
    public String eliminar(@PathVariable int idDC, ModelMap modelo) {
        try {
            dictadoClaseService.eliminarDictado(idDC);           
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        return "profesor/verDictados";
    }

    @GetMapping("/dictados/nuevo")
    public String nuevoDictado(ModelMap model) {
        model.addAttribute("materias", materiaService.listarMaterias());
        model.addAttribute("niveles", nivelService.listarNiveles());
        return "nuevo_dictado";
    }


}
