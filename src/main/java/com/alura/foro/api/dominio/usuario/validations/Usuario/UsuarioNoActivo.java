package com.alura.foro.api.dominio.usuario.validations.Usuario;

import com.alura.foro.api.dominio.usuario.DTO_ActualizarUsuario;
import com.alura.foro.api.infra.errores.exception.UnauthorizedAccessException;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioNoActivo implements ValidadorUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void updateUsuario(DTO_ActualizarUsuario datosActualizar){

        if (datosActualizar.email() == null){
            return;
        }

        var usuarioNoActivo = usuarioRepository.findActivoByEmail(datosActualizar.email());
        if (!usuarioNoActivo){
            throw new UnauthorizedAccessException("El usuario ya existe pero esta Inactivo");
        }

    }

}
