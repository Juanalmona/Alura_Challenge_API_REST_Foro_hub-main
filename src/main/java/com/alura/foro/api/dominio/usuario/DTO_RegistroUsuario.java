package com.alura.foro.api.dominio.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record `DTO_RegistroUsuario` que representa los datos necesarios para registrar un usuario.
 * Utiliza la funcionalidad de `record` de Java para definir un DTO inmutable y
 * incluye anotaciones de validación para asegurar que los datos cumplen con los requisitos.
 */
public record DTO_RegistroUsuario(
        @NotBlank                       // Valida que el nombre no sea nulo ni vacío.
        String nombre,

        @NotBlank                       // Valida que el email no sea nulo ni vacío.
        @Email                          // Valida que el email tenga un formato de correo electrónico válido.
        String email,

        @NotBlank                       // Valida que la contraseña no sea nula ni vacía.
        String contrasena,

        @NotNull                        // Valida que el perfil no sea nulo.
        Perfil perfil) {
}