package com.alura.foro.api.infra.security;

import com.alura.foro.api.dominio.usuario.DTO_AutenticacionUsuario;
import com.alura.foro.api.dominio.usuario.DTO_RespuestaUsuario;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import com.alura.foro.api.dominio.usuario.validations.Auth.ValidadorAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de autenticación para gestionar la autenticación de usuarios.
 */

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    List<ValidadorAuth> validadores;
    private final UsuarioRepository usuarioRepository;
    AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método para cargar un usuario por su nombre de usuario (email).
     *
     * @param username El nombre de usuario (email) del usuario.
     * @return Los detalles del usuario encontrado.
     * @throws UsernameNotFoundException Si no se encuentra el usuario.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    /**
     * Método para autenticar a un usuario.
     *
     * @param datosAutenticacion Los datos de autenticación del usuario.
     * @return Los datos de respuesta del usuario autenticado.
     */
    public DTO_RespuestaUsuario autenticarUsuario(DTO_AutenticacionUsuario datosAutenticacion) {
        // Validar los datos de autenticación
        validadores.forEach(v-> v.validar(datosAutenticacion));

        // Buscar y devolver los datos del usuario autenticado
        return new DTO_RespuestaUsuario((Usuario) usuarioRepository.findByEmail(datosAutenticacion.email()));
    }
}
