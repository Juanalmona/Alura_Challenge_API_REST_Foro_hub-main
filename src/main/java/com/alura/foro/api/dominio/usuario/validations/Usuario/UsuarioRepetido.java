package com.alura.foro.api.dominio.usuario.validations.Usuario;

import com.alura.foro.api.dominio.usuario.DTO_RegistroUsuario;
import com.alura.foro.api.infra.errores.exception.ExceptionDeAuth;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

;

@Component
public class UsuarioRepetido{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void repetido(DTO_RegistroUsuario datosRegistro){

        if (datosRegistro.email() == null){
            return;
        }

        var usuarioRepetido = usuarioRepository.findByPassword(datosRegistro.email());
        if (usuarioRepetido != null) {
            throw new ExceptionDeAuth("El Usuario ya esta registrado");
        }

    }
}
