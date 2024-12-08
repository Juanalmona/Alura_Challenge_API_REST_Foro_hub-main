package com.alura.foro.api.infra.errores.exception;

/**
 * Excepción personalizada que se lanza cuando un usuario intenta acceder a un recurso
 * para el cual no tiene permisos de autorización adecuados.
 * Extiende RuntimeException, lo que la hace una excepción no verificada (unchecked).
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error que describe la causa de la excepción de acceso no autorizado.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
