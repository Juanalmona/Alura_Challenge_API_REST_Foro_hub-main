package com.alura.foro.api.dominio.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Repositorio para la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Método para encontrar un usuario por email.
     *
     * @param username El email del usuario.
     * @return Los detalles del usuario.
     */
    UserDetails findByEmail(String username);

    /**
     * Método para encontrar usuarios activos paginados.
     *
     * @param paginacion La paginación de resultados.
     * @return Una página de usuarios activos.
     */
    Page<Usuario> findByActivoTrue(Pageable paginacion);

    /**
     * Consulta para verificar si un usuario está activo por email.
     *
     * @param username El email del usuario.
     * @return Si el usuario está activo.
     */
    @Query("""
            select u.activo 
            from Usuario u
            where u.email=:username
            """)
    Boolean findActivoByEmail(String username);

    /**
     * Consulta para encontrar un usuario por email y verificar si está activo.
     *
     * @param username El email del usuario.
     * @return El usuario activo.
     */
    @Query("""
            select u
            from Usuario u
            where u.email = :username and cast(u.activo as boolean) = true
            """)
    Usuario findByPassword(String username);

    /**
     * Método para actualizar un usuario por el administrador.
     *
     * @param id        El ID del usuario a actualizar.
     * @param nombre    El nuevo nombre del usuario.
     * @param email     El nuevo email del usuario.
     * @param contrasena La nueva contraseña del usuario.
     * @param activo    El nuevo estado activo del usuario.
     * @param perfil    El nuevo perfil del usuario.
     * @return El número de usuarios actualizados.
     */
    @Modifying
    @Query("""
            UPDATE Usuario u
                  SET
                    u.nombre = COALESCE(:nombre, u.nombre),
                    u.email = COALESCE(:email, u.email),
                    u.contrasena = COALESCE(:contrasena, u.contrasena),
                    u.perfil = COALESCE(:perfil, u.perfil),
                    u.activo = COALESCE(:activo, u.activo)
                  WHERE u.id = :id
            """)
    Integer updateUsuarioAdmin(@Param("id") Long id,
                            @Param("nombre") String nombre,
                            @Param("email") String email,
                            @Param("contrasena") String contrasena,
                            @Param("activo") Boolean activo,
                            @Param("perfil") Perfil perfil);



}


