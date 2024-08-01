package com.eoi.springwebsecurity.filemanagement.exception;

import com.eoi.springwebsecurity.filemanagement.models.ResponseMessage;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Clase que maneja las excepciones generadas durante la carga de archivos.
 * <p>
 * La anotación {@code @ControllerAdvice} indica que esta clase proporciona manejo global de excepciones
 * a través de todos los controladores de la aplicación.
 */
@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

//    /**
//     * Maneja la excepción de tamaño máximo de archivo excedido.
//     *
//     * @param exc Excepción MaxUploadSizeExceededException.
//     *
//     * @return ResponseEntity con un mensaje de error.
//     */
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Fichero demasiado grande!"));
//    }
}
