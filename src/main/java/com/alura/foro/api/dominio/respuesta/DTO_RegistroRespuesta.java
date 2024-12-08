package com.alura.foro.api.dominio.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Record que representa los datos necesarios para registrar una nueva respuesta
public record DTO_RegistroRespuesta(
        @NotBlank  // Anotación para asegurar que el mensaje no esté en blanco
        String mensaje,
        @NotNull   // Anotación para asegurar que el topicoId no sea nulo
        Long topicoId,
        @NotNull   // Anotación para asegurar que el autorId no sea nulo
        Long autorId,
        @NotBlank  // Anotación para asegurar que solucion no esté en blanco
        Boolean solucion) {
}