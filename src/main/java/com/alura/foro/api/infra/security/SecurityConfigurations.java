package com.alura.foro.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.alura.foro.api.dominio.usuario.Perfil.ADMIN;
import static com.alura.foro.api.dominio.usuario.Perfil.USER;

/**
 * Configuración de seguridad para la aplicación.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations {

    // Lista de rutas que se permiten sin autenticación
    private static final String[] AUTH_WHITELIST = {
            "/usuarios/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    // Inyección del filtro de seguridad personalizado
    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Configura la cadena de filtros de seguridad de la aplicación.
     *
     * @param httpSecurity Objeto HttpSecurity para construir la configuración de seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre algún error en la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF para APIs RESTful
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de sesión como stateless
                .authorizeHttpRequests(auth -> auth
                        // Permitir todas las solicitudes POST al endpoint /login
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        // Permitir las rutas en AUTH_WHITELIST sin autenticación
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // Permitir el registro de usuarios
                        .requestMatchers(HttpMethod.POST, "/usuario/register").permitAll()
                        // Proteger las rutas /admin/** solo para usuarios con el rol ADMIN
                        .requestMatchers("/admin/**").hasAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/cursos/**").hasAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/cursos/**").hasAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/respuestas/").hasAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/topicos/**").hasAuthority(ADMIN.name())
                        // Requerir autenticación para cualquier otra solicitud
                        .anyRequest().authenticated()
                )
                // Añadir el filtro personalizado antes del filtro de autenticación por nombre de usuario y contraseña
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configura el AuthenticationManager utilizado para la autenticación.
     *
     * @param authenticationConfiguration Configuración de autenticación proporcionada por Spring.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre algún error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura el codificador de contraseñas BCrypt utilizado para encriptar las contraseñas.
     *
     * @return El codificador BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}