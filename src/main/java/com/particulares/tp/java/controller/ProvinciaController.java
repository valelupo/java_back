package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.particulares.tp.java.entities.Provincia;
import com.particulares.tp.java.service.ProvinciaService;

@Controller
@RequestMapping("/provincia") // localhost:8080/provicia
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaServicio;

    @GetMapping("/registrar") 
    public String registrar() {
        return "provincia/form";
    }

    @PostMapping("/crear") // localhost:8080/provincia/crear
    public String crear(@RequestParam String nombre, ModelMap modelo){
        try {
            provinciaServicio.crearProvincia(nombre);   // llamo a mi servicio para persistir  
            modelo.put("exito", "La provincia fue cargada correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }        
        return "provincia/form";
}


    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("provincias", provinciaServicio.listarProvincias()); 
        return "provincia/lista";
    }


    // @GetMapping("/modificar/{id}")
    // public String modificar(@PathVariable int id, ModelMap modelo) {
    //     modelo.put("provincia", provinciaServicio.getOne(id));

    //     return "provincia_modificar.html";
    // }


    // @PostMapping("/modificar/{id}")
    // public String modificar(@PathVariable int id, @RequestParam String nombre, ModelMap modelo) {
    //     try {
    //         provinciaServicio.modificarProvincia(nombre, id);

    //         return "redirect:../lista";
            
    //     } catch (Exception ex) {
    //         modelo.put("error", ex.getMessage());
    //         return "provincia_modificar.html";
    //     }
    // }

    // @PostMapping("/eliminar/{id}")
    // public String eliminar(@PathVariable int id, ModelMap modelo) {
    //     try {
    //         provinciaServicio.eliminarProvincia(id);
    //         return "redirect:../lista";
    //     } catch (Exception ex) {
    //         modelo.put("error", ex.getMessage());
    //         return "provincia_list.html";
    //     }
    // } me parece que no hay que eliminar 

}

