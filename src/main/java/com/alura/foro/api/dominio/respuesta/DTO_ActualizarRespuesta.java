package com.alura.foro.api.dominio.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Record que representa los datos para actualizar una respuesta
public record DTO_ActualizarRespuesta(
        @NotNull  // Anotación para asegurar que el id no sea nulo
        Long id,
        @NotBlank  // Anotación para asegurar que el mensaje no sea vacío
        String mensaje,
        @NotNull  // Anotación para asegurar que el topicoId no sea nulo
        Long topicoId,
        @NotNull  // Anotación para asegurar que el autorId no sea nulo
        Long autorId,
        @NotNull  // Anotación para asegurar que la solucion no sea nulo
        Boolean solucion) {
}