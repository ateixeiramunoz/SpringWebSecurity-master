package com.eoi.springwebsecurity.coreapp.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Clase controladora de errores que maneja las excepciones producidas en la aplicación.
 */
@ControllerAdvice
@Log4j2
public class DefaultErrorControllerAdvice {

    /**
     * Controlador de excepciones genéricas que redirige a la página de error y muestra un mensaje de error
     * personalizado.
     *
     * @param throwable excepción que se ha lanzado
     * @param model     modelo que se utiliza para transmitir información a la vista
     *
     * @return la vista "error"
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("ERROR Durante la ejecución de la aplicación SpringBoot", throwable);

        // Obtiene el mensaje de error personalizado a mostrar
        String errorMessage = (throwable != null ? throwable.getLocalizedMessage() : "Unknown error");
        model.addAttribute("errorMsg", errorMessage);

        // Redirige a la página de error
        return "error";
    }

}
