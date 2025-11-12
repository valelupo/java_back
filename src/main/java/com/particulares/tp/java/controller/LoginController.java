package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.service.AlumnoService;
import com.particulares.tp.java.service.ProfesorService;
import com.particulares.tp.java.service.ProvinciaService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/")  
    public String index() {
        return "index";   
    }

    @GetMapping("/registrar") 
    public String registrar(ModelMap modelo) {
        modelo.put("provincias", provinciaService.listarProvincias());
        return "registro.html";   
    }

    @PostMapping("/registro")
    public String registro( @RequestParam String nombre, 
                            @RequestParam String apellido,
                            @RequestParam String email, 
                            @RequestParam int idLocalidad,
                            @RequestParam String clave, 
                            @RequestParam String clave2, 
                            @RequestParam String rol, 
                            @RequestParam (required = false) String telefono,
                            @RequestParam (required = false) String formaTrabajo,
                            @RequestParam (required = false) String infoAcademica,
                            @RequestParam (required = false) Double precioXHs, 
                            @RequestParam (required = false) MultipartFile imagen, ModelMap modelo) {
        try {
            if (rol.equals("ALUMNO")) {
                alumnoService.crearAlumno(nombre, apellido, email, idLocalidad, clave, clave2);
            }else if (rol.equals("PROFESOR")){
                profesorService.crearProfesor(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, precioXHs, imagen);
            }
            modelo.put("exito", "Usuario registrado correctamente!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            return "registro.html";
        }
        return "index.html";
    }

    //modelo: guardo los datos para mandarlos despues a la vista desde el controlador 

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
        if (error != null) {
               modelo.put("error", "Email o Clave inválidos!");        
        }
        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio(@RequestParam(required = false) String orden,
                        HttpSession session, ModelMap modelo){
        Persona logueado = (Persona) session.getAttribute("personaSession");
        modelo.put("persona", logueado);

        if (logueado.getRol().toString().equals("PROFESOR")) {
            modelo.put("profesor", logueado);
            return "profesor/home";
        }
        else if (logueado.getRol().toString().equals("ADMIN")){
            return "admin/home";
        }

        //para no cargar ambas listas en memoria
        if ("puntaje".equals(orden)) {
            modelo.put("profesores", profesorService.listarProfesoresPorPuntajeOrdenados());
        }else{
            modelo.put("profesores", profesorService.listarProfesoresPorPuntaje());
            // modelo.put("profesores", profesorService.listarProfesores());
        }

        return "alumno/home";
    }

    // @GetMapping("/inicio")
    // public String dashboard(Model model, Principal principal) {
    //     model.addAttribute("usuario", principal.getName());
    //     return "dashboard";
    // }
}