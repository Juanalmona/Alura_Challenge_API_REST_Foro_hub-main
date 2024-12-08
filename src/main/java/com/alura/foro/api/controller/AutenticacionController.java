package com.alura.foro.api.controller;

import com.alura.foro.api.dominio.usuario.DTO_AutenticacionUsuario;
import com.alura.foro.api.infra.security.DTO_JWTtoken;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.infra.security.AutenticacionService;
import com.alura.foro.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación - Login", description = "Gestionar la creación y verificación de tokens JWTO para el login de usuario")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;  // Gestor de autenticación de Spring Security

    @Autowired
    private TokenService tokenService;  // Servicio para la generación y validación de tokens JWT

    @Autowired
    private AutenticacionService autenticacionService;  // Servicio de autenticación personalizado

    /**
     * Endpoint para autenticar un  usuario y generar un token JWT.
     *
     * @param datosAutenticacion Datos de autenticación del usuario (email y contraseña).
     * @return Respuesta con el token JWT generado.
     */
    @PostMapping

    public ResponseEntity<DTO_JWTtoken> autenticarUsuario(
            @RequestBody @Valid DTO_AutenticacionUsuario datosAutenticacion) {

        // Crear un token de autenticación con el email y contraseña proporcionados
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacion.email(), datosAutenticacion.contrasena());

        // Autenticar al usuario utilizando el AuthenticationManager de Spring Security
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Generar un token JWT para el usuario autenticado
        String JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devolver el token JWT en la respuesta
        return ResponseEntity.ok(new DTO_JWTtoken(JWTtoken));
    }
}