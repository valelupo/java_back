package com.particulares.tp.java.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.particulares.tp.java.entities.Localidad;
import com.particulares.tp.java.service.LocalidadService;
import com.particulares.tp.java.service.ProvinciaService;


@Controller
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/listaProv")
    @ResponseBody
    public List<Localidad> obtenerLocalidadesPorProvincia(@RequestParam int idProvincia) {
        return localidadService.buscarPorProvincia(idProvincia);
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {

        model.addAttribute("provincias", provinciaService.listarProvincias());
        return "admin/localidad/form";
    }
    
    @PostMapping("/registro")
    public String registrarLocalidad(
            @RequestParam Integer idProvincia,
            @RequestParam String nombre,
            @RequestParam Integer codPostal,
            ModelMap model) {

        try {
            Localidad localidadRegistrada = localidadService.crearLocalidad(nombre, codPostal, idProvincia);
            
            model.put("exito", "La localidad fue registrada exitosamente");
            model.put("localidadRegistrada",localidadRegistrada.getId());

        } catch (Exception e) {
            model.put("error", e.getMessage());
        }

        model.addAttribute("provincias", provinciaService.listarProvincias());
        return "admin/localidad/form";
    }

    @GetMapping("/listar")
    public String listarLocalidades(@RequestParam(required = false) String provincia, Model model) {
        try {
            List<Localidad> localidades = localidadService.listarLocalidades();

            if (provincia != null && !provincia.isEmpty()) {
                localidades = localidadService.listarLocalidadesPorProvincia(provincia);
            } else {
                localidades = localidadService.listarLocalidades();
            }
            
            model.addAttribute("localidades", localidades);
            model.addAttribute("provinciaSeleccionada", provincia);
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar agentes: " + e.getMessage());
        }
        return "admin/localidad/lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            localidadService.eliminarLocalidad(id);
            redirectAttributes.addFlashAttribute("exito", "Localidad eliminada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/localidad/listar";
    } 
}


