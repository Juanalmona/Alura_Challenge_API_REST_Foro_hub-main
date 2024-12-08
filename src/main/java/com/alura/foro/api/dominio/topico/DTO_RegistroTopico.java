package com.alura.foro.api.dominio.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record que encapsula los datos necesarios para registrar un nuevo tema (topico) en el foro.
 * Incluye el título del tema, el mensaje, el ID del autor y el ID del curso al que está asociado.
 */
public record DTO_RegistroTopico(
        @NotBlank // Asegura que el título no esté en blanco
        String titulo, // Título del tema

        @NotBlank // Asegura que el mensaje no esté en blanco
        String mensaje, // Mensaje o contenido del tema

        @NotNull // Asegura que el autorId no sea nulo
        Long autorId, // ID del autor del tema

        @NotNull // Asegura que el cursoId no sea nulo
        Long cursoId // ID del curso asociado al tema
) {
}