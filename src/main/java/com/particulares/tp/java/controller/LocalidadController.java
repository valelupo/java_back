package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.service.LocalidadService;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {
    @Autowired
    private LocalidadService localidadService;

    @GetMapping("/listaProv")
    @ResponseBody
    public List<Localidad> obtenerLocalidadesPorProvincia(@RequestParam int provinciaId) {
        return localidadService.buscarPorProvincia(provinciaId);
    }

    // @GetMapping("/lista/{id}")
    // public String listar(@PathVariable int id, ModelMap modelo) {

    //     modelo.addAttribute("localidadesProv", localidadService.buscarPorProvincia(id)); 

    //     return "localidad/listaProv.html";
    // }
}
