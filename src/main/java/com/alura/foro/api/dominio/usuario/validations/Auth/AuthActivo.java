package com.alura.foro.api.dominio.usuario.validations.Auth;

import com.alura.foro.api.dominio.usuario.DTO_AutenticacionUsuario;
import com.alura.foro.api.infra.errores.exception.ExceptionDeAuth;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;;

@Component
public class AuthActivo implements ValidadorAuth {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(DTO_AutenticacionUsuario datosAutenticacion){

        if (datosAutenticacion.email() == null){
            return;
        }

        var usuarioActivo = usuarioRepository.findActivoByEmail(datosAutenticacion.email());
        if (usuarioActivo == null){
            throw new ExceptionDeAuth("El Usuario no existe");
        }

        if (!usuarioActivo){
            throw new ExceptionDeAuth("El Usuario no esta Activo");
        }

    }
}
