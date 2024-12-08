package com.alura.foro.api.dominio.usuario;

/**
 * Record `DTO_RespuestaUsuarioId` que representa la respuesta de un usuario con su ID.
 * Esta clase utiliza la funcionalidad de `record` de Java para definir un DTO inmutable.
 */
public record DTO_RespuestaUsuarioId(
        String nombre,
        String perfil) {

    /**
     * Constructor que acepta un objeto `Usuario` y extrae sus campos para inicializar el record.
     *
     * @param usuario El objeto `Usuario` del cual se extraen los datos.
     */
    public DTO_RespuestaUsuarioId(Usuario usuario) {
        this(
                usuario.getNombre(),       // Inicializa el campo `nombre` con el nombre del usuario.
                usuario.getPerfil().toString() // Convierte el perfil del usuario a String para inicializar el campo `perfil`.
        );
    }
}
