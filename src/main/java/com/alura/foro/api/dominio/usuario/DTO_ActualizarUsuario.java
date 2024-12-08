package com.alura.foro.api.dominio.usuario;

/**
 * Record `DTO_ActualizarUsuario` que representa los datos necesarios para actualizar un usuario.
 * Utiliza la funcionalidad de `record` de Java para definir un DTO inmutable.
 */
public record DTO_ActualizarUsuario(
        Long id,         // El ID único del usuario.
        String nombre,   // El nuevo nombre del usuario.
        String email,    // El nuevo correo electrónico del usuario.
        String contrasena, // La nueva contraseña del usuario.
        Perfil perfil,   // El nuevo perfil del usuario.
        Boolean activo) { // El nuevo estado de activo/inactivo del usuario.

}