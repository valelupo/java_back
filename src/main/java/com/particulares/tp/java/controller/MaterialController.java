package com.particulares.tp.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.particulares.tp.java.entities.Profesor;
import com.particulares.tp.java.service.MaterialService;

import jakarta.servlet.http.HttpSession;

public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/registrar")
    public String registrar(HttpSession session,
                            ModelMap model) {
        return "profesor/material";
    }


    @PostMapping("/registro")
    public String registrarDocumentos(@RequestParam MultipartFile[] archivos,
                                    @RequestParam String[] descripciones,
                                    HttpSession session,
                                    ModelMap model) {
        try {
            Profesor profesor = (Profesor) session.getAttribute("profesor"); // ver de hacer con el Auth
            materialService.crearmaterial(descripciones, profesor.getId(), archivos);

            model.put("exito", "Documentos cargados correctamente.");
            return "profesor/home"; // siguiente paso
        } catch (Exception e) {
            model.put("error", "Error al cargar documentos: " + e.getMessage());
            return "profesor/material";
        }
    }


}
