package com.alura.foro.api.infra.errores.exception;

/**
 * Excepción personalizada que se lanza en casos de errores de autenticación o autorización.
 * Extiende RuntimeException, permitiendo manejarla como una excepción no verificada.
 */
public class ExceptionDeAuth extends RuntimeException {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error que describe la causa de la excepción de autenticación o autorización.
     */
    public ExceptionDeAuth(String message) {
        super(message);
    }
}
