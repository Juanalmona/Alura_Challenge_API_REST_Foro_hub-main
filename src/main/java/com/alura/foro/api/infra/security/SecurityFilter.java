package com.alura.foro.api.infra.security;

import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Esta clase actúa como un filtro de seguridad para autenticar solicitudes HTTP.
 * Extiende OncePerRequestFilter para asegurar que el filtro se ejecute una vez por cada solicitud.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    // Inyecta el servicio de token para validar y extraer información de los tokens JWT.
    @Autowired
    private TokenService tokenService;

    // Inyecta el repositorio de usuarios para buscar usuarios en la base de datos.
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Este método se ejecuta en cada solicitud HTTP para verificar y procesar la autenticación.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros que permite pasar la solicitud al siguiente filtro.
     * @throws ServletException Si ocurre un error durante la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene el valor del encabezado "Authorization" de la solicitud.
        var authHeader = request.getHeader("Authorization");

        // Verifica si el encabezado "Authorization" está presente y comienza con "Bearer ".
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extrae el token JWT eliminando el prefijo "Bearer ".
            String token = authHeader.replace("Bearer ", "");

            // Utiliza el servicio de token para obtener el "subject" (generalmente el email del usuario) del token.
            String subject = tokenService.getSubject(token);

            // Verifica si se pudo extraer el "subject" del token.
            if (subject != null) {

                // Busca el usuario en la base de datos por su email (el "subject" extraído).
                Usuario usuario = (Usuario) usuarioRepository.findByEmail(subject);

                // Verifica si el usuario existe y está activo.
                if (usuario != null && usuario.getActivo()) {
                    // Crea un objeto de autenticación con el usuario y sus roles/authorities.
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities());

                    // Establece el contexto de seguridad con la autenticación del usuario.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continúa con el siguiente filtro en la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}