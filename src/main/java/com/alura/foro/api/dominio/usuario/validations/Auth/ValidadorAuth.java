package com.alura.foro.api.dominio.usuario.validations.Auth;

import com.alura.foro.api.dominio.usuario.DTO_AutenticacionUsuario;

public interface ValidadorAuth {

    public void validar(DTO_AutenticacionUsuario datosAutenticacion);
}
