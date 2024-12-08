package com.alura.foro.api.dominio.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Clase que representa a un usuario en el sistema.
 */
@Table(name = "usuarios")  // Define el nombre de la tabla en la base de datos
@Entity(name = "Usuario")  // Define el nombre de la entidad en JPA
@Getter  // Genera getters automáticos para todos los campos
@Setter  // Genera setters automáticos para todos los campos
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@EqualsAndHashCode(of = "id")  // Genera automáticamente equals() y hashCode() usando solo el campo "id"
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del usuario

    private String nombre;  // Nombre del usuario

    @Email
    private String email;  // Correo electrónico del usuario

    private String contrasena;  // Contraseña del usuario

    @Enumerated(EnumType.STRING)
    private Perfil perfil;  // Perfil del usuario, definido por un enum

    private Boolean activo;  // Estado de activo/inactivo del usuario

    /**
     * Constructor para crear un usuario a partir de datos de registro.
     *
     * @param datos Los datos de registro del usuario.
     */
    public Usuario(DTO_RegistroUsuario datos) {
        this.activo = true;  // Se activa por defecto al registrarse
        this.nombre = datos.nombre();  // Inicializa el nombre desde los datos proporcionados
        this.email = datos.email();  // Inicializa el correo electrónico desde los datos proporcionados
        this.contrasena = datos.contrasena();  // Inicializa la contraseña desde los datos proporcionados
        this.perfil = datos.perfil();  // Asigna el perfil directamente desde los datos
    }

    /**
     * Método para actualizar los datos de un usuario.
     *
     * @param datosActualizar Los datos actualizados del usuario.
     */
    public void actualizarDatos(DTO_ActualizarUsuario datosActualizar) {
        if (datosActualizar.nombre() != null) {
            this.nombre = datosActualizar.nombre();  // Actualiza el nombre si se proporciona uno nuevo
        }
        if (datosActualizar.email() != null) {
            this.email = datosActualizar.email();  // Actualiza el correo electrónico si se proporciona uno nuevo
        }
        if (datosActualizar.contrasena() != null) {
            this.contrasena = datosActualizar.contrasena();  // Actualiza la contraseña si se proporciona una nueva
        }
        if (datosActualizar.perfil() != datosActualizar.perfil()) {
            this.perfil = datosActualizar.perfil();  // Actualiza el perfil si se proporciona uno nuevo
        }
        if (datosActualizar.activo() != datosActualizar.activo()) {
            this.activo = datosActualizar.activo();  // Actualiza el estado de activo/inactivo si se cambia
        }
    }

    /**
     * Método para desactivar un usuario.
     */
    public void desactivarUsuario() {
        this.activo = false;  // Establece el estado de activo a falso para desactivar el usuario
    }

    /**
     * Método para obtener los roles o permisos del usuario.
     *
     * @return Una colección de roles del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(perfil.toString()));  // Devuelve el perfil del usuario como autoridad
    }

    /**
     * Método para obtener la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return contrasena;  // Devuelve la contraseña del usuario
    }

    /**
     * Método para obtener el nombre de usuario del usuario.
     *
     * @return El nombre de usuario del usuario.
     */
    @Override
    public String getUsername() {
        return email;  // Devuelve el correo electrónico como nombre de usuario
    }

    /**
     * Método para verificar si la cuenta del usuario no ha expirado.
     *
     * @return Si la cuenta del usuario no ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;  // La cuenta nunca expira en este ejemplo
    }

    /**
     * Método para verificar si la cuenta del usuario no está bloqueada.
     *
     * @return Si la cuenta del usuario no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;  // La cuenta nunca se bloquea en este ejemplo
    }

    /**
     * Método para verificar si las credenciales del usuario no han expirado.
     *
     * @return Si las credenciales del usuario no han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Las credenciales nunca expiran en este ejemplo
    }

    /**
     * Método para verificar si el usuario está activo.
     *
     * @return Si el usuario está activo.
     */
    @Override
    public boolean isEnabled() {
        return true;  // El usuario siempre está activo en este ejemplo
    }
}