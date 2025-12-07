package com.particulares.tp.java.controller;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc,
                                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "El archivo supera el tamaño máximo permitido.");
        return "redirect:/registrar";
    }
}
