package com.particulares.tp.java.controller;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxSizeException(MaxUploadSizeExceededException exc,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("error", "El archivo supera el tamaño máximo permitido.");

        String uri = request.getRequestURI();
        if (uri.startsWith("/material")) {
            return "redirect:/material/registrar";
        } else{
            return "redirect:/registrar";
        }
   
    }
}
