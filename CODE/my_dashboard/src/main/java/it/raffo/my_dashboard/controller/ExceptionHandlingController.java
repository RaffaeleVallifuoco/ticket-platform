package it.raffo.my_dashboard.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExceptionHandlingController {

    // @ExceptionHandler(AccessDeniedException.class)
    @GetMapping("/403")
    public String handleAccessDeniedException() {
        return "/common/403"; // Nome della pagina di errore personalizzata
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException() {
        return "error"; // Pagina di errore generica
    }

}
