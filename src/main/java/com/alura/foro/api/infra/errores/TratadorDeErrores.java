package com.alura.foro.api.infra.errores;

import com.alura.foro.api.infra.errores.exception.ExceptionDeAuth;
import com.alura.foro.api.infra.errores.exception.UnauthorizedAccessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase para manejar los errores de la aplicación.
 */
@RestControllerAdvice
public class TratadorDeErrores {

    /**
     * Manejador para errores de entidad no encontrada (404).
     *
     * @return Respuesta HTTP 404 Not Found.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Manejador para errores de validación de argumentos (400).
     *
     * @param e La excepción de validación.
     * @return Respuesta HTTP 400 Bad Request con detalles de los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Manejador para excepciones de autenticación.
     *
     * @param e La excepción de autenticación.
     * @return Respuesta HTTP 400 Bad Request con el mensaje de la excepción.
     */
    @ExceptionHandler(ExceptionDeAuth.class)
    public ResponseEntity errorHandlerExceptionIntegridad(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
            * Manejador para excepciones de acceso denegado.
            *
            * @param e La excepción de acceso denegado.
            * @return Respuesta HTTP 403 Forbidden con el mensaje de acceso denegado.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAccessDeniedException(Exception e) {
        String mensaje = "No tienes permitido hacer esta accion, comunicate con tu ADMIN";

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mensaje);
    }

    /**
     * Manejador para excepciones de acceso no autorizado.
     *
     * @param e La excepción de acceso no autorizado.
     * @return Respuesta HTTP 400 Bad Request con el mensaje de la excepción.
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity UnauthorizedAccessException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Clase auxiliar para representar errores de validación.
     */
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
