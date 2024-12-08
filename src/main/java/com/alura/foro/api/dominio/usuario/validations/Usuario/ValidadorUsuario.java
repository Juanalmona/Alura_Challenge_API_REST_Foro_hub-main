package com.alura.foro.api.dominio.usuario.validations.Usuario;

import com.alura.foro.api.dominio.usuario.DTO_ActualizarUsuario;

public interface ValidadorUsuario {

    public void updateUsuario(DTO_ActualizarUsuario datosActualizar);
}
