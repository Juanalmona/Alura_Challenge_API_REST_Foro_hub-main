package com.alura.foro.api.dominio.usuario.validations.Usuario;

import com.alura.foro.api.dominio.usuario.DTO_ActualizarUsuario;
import com.alura.foro.api.infra.errores.exception.UnauthorizedAccessException;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioID implements ValidadorUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void updateUsuario(DTO_ActualizarUsuario datosActualizar) {

        // Validar que el ID no sea nulo
        if (datosActualizar.id() == null) {
            throw new UnauthorizedAccessException("El ID no puede ser nulo");
        }

    }

}
