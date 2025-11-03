package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "index.html";   
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
                            @RequestParam (required = false) Integer matricula, ModelMap modelo) {
        try {
            if (rol.equals("ALUMNO")) {
                alumnoService.crearAlumno(nombre, apellido, email, idLocalidad, clave, clave2);
            }else if (rol.equals("PROFESOR")){
                profesorService.crearProfesor(nombre, apellido, email, idLocalidad, clave, clave2, telefono, formaTrabajo, infoAcademica, matricula);
            }
            modelo.put("exito", "Usuario registrado correctamente!");
        } catch (Exception e) {
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
    public String inicio(HttpSession session){
        //Persona logueado = (Persona) session.getAttribute("personasession");
        // if (logueado.getRol().toString().equals("PELUQUERO")) {
        //     return "redirect:/peluquero/inicio";
        // }
        return "iniAlumno.html";
    }

    // @GetMapping("/inicio")
    // public String dashboard(Model model, Principal principal) {
    //     model.addAttribute("usuario", principal.getName());
    //     return "dashboard";
    // }
}