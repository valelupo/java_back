package com.particulares.tp.java.controller;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
                || (request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json"));

        if (isAjax) {
            // Para fetch: no redirigir, devolver 413
            return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("El archivo supera el tamaño máximo permitido.");
        }

        // Para form tradicional: redirigir con flash message
        redirectAttributes.addFlashAttribute("error", "El archivo supera el tamaño máximo permitido.");

        String uri = request.getRequestURI();
        if (uri.startsWith("/material")) {
            return "redirect:/material/registrar";
        } else{
            return "redirect:/registrar";
        }
   
    }
}
