package com.alura.foro.api.dominio.usuario;

/**
 * Record `DTO_ListadoUsuario` que representa la información básica de un usuario para listados.
 * Utiliza la funcionalidad de `record` de Java para definir un DTO inmutable.
 */
public record DTO_ListadoUsuario(
        Long id,         // El ID único del usuario.
        String nombre,   // El nombre del usuario.
        String perfil, // El perfil del usuario convertido a String.
        Boolean activo) {
    /**
     * Constructor que acepta un objeto `Usuario` y extrae sus campos para inicializar el record.
     *
     * @param usuario El objeto `Usuario` del cual se extraen los datos.
     */
    public DTO_ListadoUsuario(Usuario usuario) {
        this(
                usuario.getId(),               // Inicializa el campo `id` con el ID del usuario.
                usuario.getNombre(),           // Inicializa el campo `nombre` con el nombre del usuario.
                usuario.getPerfil().toString(), // Convierte el perfil del usuario a String para inicializar el campo `perfil`.
                usuario.getActivo()
        );
    }
}
