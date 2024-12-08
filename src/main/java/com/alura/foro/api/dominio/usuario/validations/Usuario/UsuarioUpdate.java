package com.alura.foro.api.dominio.usuario.validations.Usuario;

import com.alura.foro.api.dominio.usuario.DTO_ActualizarUsuario;
import com.alura.foro.api.infra.errores.exception.UnauthorizedAccessException;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUpdate implements ValidadorUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void updateUsuario(DTO_ActualizarUsuario datosActualizar) {

        // Validar que el ID y el perfil no sean diferentes a los de la base de datos
        Usuario usuario = usuarioRepository.findById(datosActualizar.id()).get();
        if (datosActualizar.id() != usuario.getId() ||
                datosActualizar.perfil() != usuario.getPerfil() ||
                datosActualizar.activo() != usuario.getActivo()) {
            throw new UnauthorizedAccessException("Solo el ADMIN puede actualizar el perfil");
        }

    }

}
