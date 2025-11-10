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
import com.particulares.tp.java.service.ProvinciaService;


@Controller
@RequestMapping("/provincia") 
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaServicio;

    @GetMapping("/registrar") 
    public String registrar() {
        return "admin/provincia/form";
    }

    @PostMapping("/crear") 
    public String crear(@RequestParam String nombre, ModelMap modelo){
        try {
            provinciaServicio.crearProvincia(nombre);   // llamo a mi servicio para persistir  
            modelo.put("exito", "La provincia fue cargada correctamente");
        
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }        
        return "admin/provincia/form";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        modelo.addAttribute("provincias", provinciaServicio.listarProvincias()); 
        return "admin/provincia/lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            provinciaServicio.eliminarProvincia(id);
            redirectAttributes.addFlashAttribute("exito", "Provincia eliminada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/provincia/listar";
    } 

}

