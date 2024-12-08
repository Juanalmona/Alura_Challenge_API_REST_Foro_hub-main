package com.alura.foro.api.dominio.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Registro de datos necesario para crear un nuevo curso.
 * Este record encapsula los campos requeridos para registrar un curso en el sistema.
 */
public record DTO_RegistroCursos(
        @NotBlank(message = "El nombre del curso no puede estar en blanco.")
        String nombre,

        @NotNull(message = "La categoría del curso no puede ser nula.")
        String categoria) {
}