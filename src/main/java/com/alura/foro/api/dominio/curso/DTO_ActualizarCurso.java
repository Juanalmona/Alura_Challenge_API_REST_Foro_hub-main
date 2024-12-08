package com.alura.foro.api.dominio.curso;

import jakarta.validation.constraints.NotNull;
/**
 * Record que representa los datos necesarios para actualizar un curso en el sistema.
 */
public record DTO_ActualizarCurso(
        @NotNull Long id,   // Identificador único del curso a actualizar
        String nombre       // Nuevo nombre del curso (opcional, puede ser nulo)
) {
}