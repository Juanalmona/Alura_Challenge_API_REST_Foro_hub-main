package com.alura.foro.api.infra.errores.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta crear un recurso que ya existe.
 * Extiende RuntimeException, lo que permite manejarla de manera no verificada.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}