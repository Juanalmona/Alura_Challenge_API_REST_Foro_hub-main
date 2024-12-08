package com.alura.foro.api.dominio.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record `DTO_AutenticacionUsuario` que representa los datos necesarios para autenticar un usuario.
 * Utiliza la funcionalidad de `record` de Java para definir un DTO inmutable y
 * incluye anotaciones de validación para asegurar que los datos cumplen con los requisitos.
 */
public record DTO_AutenticacionUsuario(
        @NotBlank                       // Valida que el email no sea nulo ni vacío.
        @Email                          // Valida que el email tenga un formato de correo electrónico válido.
        String email,

        @NotBlank                       // Valida que la contraseña no sea nula ni vacía.
        String contrasena) {
}