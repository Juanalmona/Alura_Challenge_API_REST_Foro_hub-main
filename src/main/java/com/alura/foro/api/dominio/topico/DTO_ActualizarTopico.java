package com.alura.foro.api.dominio.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record que encapsula los datos necesarios para actualizar un tema (topico).
 * Incluye el ID, título, mensaje, estado, ID del autor y ID del curso asociado.
 */
public record DTO_ActualizarTopico(
        @NotNull // Anotación para asegurar que el ID no sea nulo
        Long id, // Identificador único del tema a actualizar

        @NotBlank // Anotación para asegurar que el título no esté en blanco
        String titulo, // Nuevo título del tema

        @NotBlank // Anotación para asegurar que el mensaje no esté en blanco
        String mensaje, // Nuevo mensaje o contenido del tema

        @NotNull // Anotación para asegurar que el estado no sea nulo
        Estado estado, // Nuevo estado del tema (por ejemplo, NO_RESPONDIDO, SOLUCIONADO, etc.)

        @NotNull // Anotación para asegurar que el ID del autor no sea nulo
        Long autorId, // ID del nuevo autor del tema

        @NotNull // Anotación para asegurar que el ID del curso no sea nulo
        Long cursoId // ID del nuevo curso asociado al tema
) {
}
