package com.alura.foro.api.dominio.usuario;

/**
 * Clase para representar los datos de respuesta de un usuario.
 */

public record DTO_RespuestaUsuario(
        Long id,
        String nombre,
        String perfil,
        Boolean activo) {

    /**
     * Constructor para convertir un objeto Usuario en DTORespuestaUsuario.
     *
     * @param usuario El usuario del cual se obtendrán los datos.
     */

    public DTO_RespuestaUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getPerfil().toString(),
                usuario.getActivo());
    }

}
