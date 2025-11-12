package com.particulares.tp.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.repository.LocalidadRepository;
import com.particulares.tp.java.service.DictadoClaseService;
import com.particulares.tp.java.service.MaterialService;
import com.particulares.tp.java.service.ProfesorService;
import com.particulares.tp.java.service.ReseniaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private DictadoClaseService dictadoClaseService;

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private LocalidadRepository localidadRepository;

    @GetMapping("/ver/{id}")
    public String verProfesor(@PathVariable Integer id, ModelMap modelo) {
        //modelo.put("materiales", materialService.listarMaterialesPorProfesor(id););
        modelo.put("promedio", reseniaService.promedioReseniasPorProfesor(id));
        modelo.put("dictados", dictadoClaseService.obtenerDictadosPorProfesor(id));
        modelo.put("profesor", profesorService.getOne(id));
        // modelo.put("resenias", reseniaService.);
        return "alumno/verProfesor";
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenProfesor(@PathVariable int id) {
        Profesor profesor = profesorService.getOne(id); 

        if (profesor.getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
            return new ResponseEntity<>(profesor.getImagen(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/perfil")
    // public String perfil(HttpSession session, ModelMap modelo) {
    //     Profesor profesor = (Profesor) session.getAttribute("personaSession");

    //     if (profesor == null) {
    //         modelo.addAttribute("error", "Debe iniciar sesión para ver su perfil.");
    //         return "redirect:/login";
    //     }

    //     modelo.addAttribute("profesor", profesor);
    //     return "profesor/perfil";
    // }

    // @GetMapping("/editar")
    // public String editarPerfil(HttpSession session, ModelMap modelo) {
    //     Profesor profesor = (Profesor) session.getAttribute("personaSession");

    //     if (profesor == null) {
    //         return "redirect:/login";
    //     }

    //     modelo.addAttribute("profesor", profesor);
    //     modelo.addAttribute("localidades", localidadRepository.findAll());
    //     return "profesor/editarPerfil";
    // }

    // @PostMapping("/editar")
    // public String actualizarPerfil(
    //         @RequestParam String nombre,
    //         @RequestParam String apellido,
    //         @RequestParam String email,
    //         @RequestParam int idLocalidad,
    //         @RequestParam String clave,
    //         @RequestParam String clave2,
    //         @RequestParam String telefono,
    //         @RequestParam String formaTrabajo,
    //         @RequestParam String infoAcademica,
    //         @RequestParam Double precioXHs,
    //         @RequestParam(required = false) MultipartFile imagen,
    //         HttpSession session,
    //         RedirectAttributes redirectAttributes) {

    //     Profesor profesor = (Profesor) session.getAttribute("personaSession");

    //     if (profesor == null) {
    //         return "redirect:/login";
    //     }

    //     try {
    //         profesorService.modificarProfesor(nombre, apellido, email, idLocalidad, clave, clave2,
    //                 telefono, formaTrabajo, infoAcademica, precioXHs, profesor.getId(), imagen);

    //         redirectAttributes.addFlashAttribute("exito", "Perfil actualizado correctamente.");
    //         return "redirect:/profesor/perfil";

    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("error", e.getMessage());
    //         return "redirect:/profesor/editar";
    //     }
    // }

    // @PostMapping("/eliminar")
    // public String eliminarProfesor(HttpSession session, RedirectAttributes redirectAttributes) {
    //     Profesor profesor = (Profesor) session.getAttribute("personaSession");

    //     if (profesor == null) {
    //         return "redirect:/login";
    //     }

    //     try {
    //         profesorService.eliminarProfesor(profesor.getId());
    //         session.invalidate();
    //         redirectAttributes.addFlashAttribute("exito", "Cuenta eliminada correctamente.");
    //         return "redirect:/";

    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("error", e.getMessage());
    //         return "redirect:/profesor/perfil";
    //     }
    // }

}
