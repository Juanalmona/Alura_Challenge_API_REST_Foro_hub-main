package com.alura.foro.api.infra.security;

import com.alura.foro.api.dominio.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Servicio para gestionar la creación y verificación de tokens JWT.
 */
@Service
public class   TokenService {

    // Inyecta el valor del secreto API desde las propiedades de la aplicación.
    @Value("${api.security.secret}")
    private String apiSecret;

    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param usuario El usuario para el cual se genera el token.
     * @return El token JWT generado.
     */
    public String generarToken(Usuario usuario) {
        try {
            // Crea el algoritmo HMAC256 utilizando el secreto API.
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            // Construye y firma el token JWT con las siguientes reclamaciones:
            // - Emisor (issuer)
            // - Sujeto (subject), que es el email del usuario
            // - ID del usuario
            // - Perfil del usuario
            // - Fecha de expiración
            return JWT.create()
                    .withIssuer("foro alura")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withClaim("perfil", usuario.getPerfil().name()) // Agrega el perfil como claim
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            // Maneja la excepción si ocurre un error al crear el token.
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    /**
     * Extrae el sujeto (subject) de un token JWT.
     *
     * @param token El token JWT del cual se extrae el subject.
     * @return El subject extraído del token.
     * @throws RuntimeException Si el token es nulo o no se puede verificar.
     */
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token no proporcionado");
        }
        try {
            // Crea el algoritmo HMAC256 utilizando el secreto API.
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            // Verifica el token JWT y extrae el subject.
            var verifier = JWT.require(algorithm)
                    .withIssuer("foro alura")
                    .build()
                    .verify(token);

            return verifier.getSubject();
        } catch (JWTVerificationException exception) {
            // Maneja la excepción si ocurre un error al verificar el token.
            throw new RuntimeException("Error al verificar el token JWT", exception);
        }
    }

    /**
     * Genera la fecha de expiración para el token JWT.
     * La fecha de expiración es 2 horas a partir del momento actual.
     *
     * @return La fecha de expiración como un objeto Instant.
     */
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}