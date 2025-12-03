package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.particulares.tp.java.entities.Material;
import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.service.DictadoClaseService;
import com.particulares.tp.java.service.MaterialService;
import com.particulares.tp.java.service.ProfesorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private DictadoClaseService dictadoClaseService;

    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/registrar")
    public String registrar(HttpSession session,
                            ModelMap model) {
        Profesor profesor = (Profesor) session.getAttribute("personaSession"); 
        model.put("dictados", dictadoClaseService.obtenerDictadosPorProfesor(profesor.getId()));
        model.put("profesor", profesor);
        return "material/crear";
    }

    @PostMapping("/registro")
    public String registrarDocumentos(@RequestParam MultipartFile[] archivos,
                                    @RequestParam String[] descripciones,
                                    @RequestParam int idDictadoClase,
                                    RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            materialService.crearmaterial(descripciones, idDictadoClase, archivos);

            redirectAttributes.addFlashAttribute("exito", "Documentos cargados correctamente.");
                return "redirect:/material/listar/" + ((Profesor) session.getAttribute("personaSession")).getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar documentos: " + e.getMessage());
            return "material/crear";
        }
    }

    @GetMapping("/listar/{id}") 
    public String listarMateriales(@PathVariable int id,
                                    ModelMap model, HttpSession session) { 
        Persona persona = (Persona) session.getAttribute("personaSession");
        if (persona instanceof Profesor) {
            boolean esProfe = (persona != null && persona.getId() == id);
            model.put("esProfe", esProfe);
        } 
        model.put("profesor", profesorService.getOne(id));
        model.put("materiales", materialService.listarMaterialesPorProfesor(id));
        return "material/lista";
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<byte[]> materialProfesor(@PathVariable int id) {
        Material material = materialService.getOne(id);
        byte[] archivo = material.getArchivo();

        if (archivo != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); 
            headers.setContentDisposition(ContentDisposition.inline().filename(material.getDescripcion() + ".pdf").build());
            return new ResponseEntity<>(archivo, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, 
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {
        try {
            materialService.eliminarMaterial(id);
            redirectAttributes.addFlashAttribute("exito", "Material eliminado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/material/listar/" + ((Profesor) session.getAttribute("personaSession")).getId(); // cambiar
    } 

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable int id,
                            ModelMap model) {
        Material material = materialService.getOne(id);
        model.put("material", material);
        model.put("dictados", dictadoClaseService.obtenerDictadosPorProfesor(material.getDictadoClase().getProfesor().getId()));
        return "material/modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificarMaterial(@PathVariable int id,
                                    @RequestParam String descripcion,
                                    @RequestParam int idDictadoClase,
                                    @RequestParam MultipartFile archivo,
                                    RedirectAttributes redirectAttributes) {
        try {
            materialService.modificarMaterial(descripcion, idDictadoClase, id, archivo);
            redirectAttributes.addFlashAttribute("exito", "Material modificado correctamente.");
            return "redirect:/material/listar/" + ((Profesor) materialService.getOne(id).getDictadoClase().getProfesor()).getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al modificar el material: " + e.getMessage());
            return "redirect:/material/modificar/" + id;
        }
    }
}
