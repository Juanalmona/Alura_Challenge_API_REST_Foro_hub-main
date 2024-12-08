package com.alura.foro.api.dominio.usuario;

import com.alura.foro.api.dominio.usuario.validations.Usuario.UsuarioID;
import com.alura.foro.api.dominio.usuario.validations.Usuario.UsuarioRepetido;
import com.alura.foro.api.dominio.usuario.validations.Usuario.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para operaciones relacionadas con los usuarios.
 */
@Service
public class     UsuarioService {

    @Autowired
    private UsuarioRepetido usuarioRepetido;
    @Autowired
    private UsuarioID usuarioID;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    List<ValidadorUsuario> validadores;


    /**
     * Método para crear un nuevo usuario, desde ADMIN.
     *
     * @param datosRegistro Los datos de registro del nuevo usuario.
     * @return Los datos de respuesta del usuario creado.
     */
    public DTO_RespuestaUsuario crearUsuario(DTO_RegistroUsuario datosRegistro) {
        // Validar si el usuario ya existe
        usuarioRepetido.repetido(datosRegistro);

        // Crear un nuevo usuario con los datos recibidos
        Usuario usuario = new Usuario(datosRegistro);
        usuario.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
        return new DTO_RespuestaUsuario(usuario);
    }

    /**
     * Método para crear un nuevo usuario, USER.
     *
     * @param datosRegistro Los datos de registro del nuevo usuario.
     * @return Los datos de respuesta del usuario creado con perfil USER.
     */
    public DTO_RespuestaUsuario crearUsuarioUser(DTO_RegistroUsuario datosRegistro) {
        // Validar si el usuario ya existe
        usuarioRepetido.repetido(datosRegistro);
        // Crear un nuevo usuario con los datos recibidos
        Usuario usuario = new Usuario(datosRegistro);
        usuario.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
        usuario.setPerfil(Perfil.USER);
        usuarioRepository.save(usuario);
        return new DTO_RespuestaUsuario(usuario);
    }

    /**UsuarioServiceUsuarioService
            * Método para actualizar un usuario.
     *
             * @param datosActualizar Los datos actualizados del usuario.
            * @return Los datos de respuesta del usuario actualizado.
     */
    public DTO_RespuestaUsuario actualizarUsuario(DTO_ActualizarUsuario datosActualizar) {
        // Validar los datos de actualización
        validadores.forEach(v-> v.updateUsuario(datosActualizar));
        // Buscar el usuario por su ID y actualizar sus datos
        Usuario usuario = usuarioRepository.findById(datosActualizar.id()).get();
        usuario.actualizarDatos(datosActualizar);
        usuario.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
        return new DTO_RespuestaUsuario(usuario);
    }

    /**
     * Método para actualizar un usuario por un administrador.
     *
     * @param datosActualizar Los datos actualizados del usuario.
     * @return Los datos de respuesta del usuario actualizado.
     */
    public DTO_RespuestaUsuario actualizarUsuarioAdmin(DTO_ActualizarUsuario datosActualizar) {
        // Validar el ID del usuario a actualizar
        usuarioID.updateUsuario(datosActualizar);

        // Obtener el usuario actualizado de la base de datos
        Usuario usuario= usuarioRepository.findById(datosActualizar.id()).get();

        // Actualizar el usuario en la base de datos usando una consulta personalizada
        usuarioRepository.updateUsuarioAdmin(
                datosActualizar.id(),
                datosActualizar.nombre(),
                datosActualizar.email(),
                datosActualizar.contrasena(),
                datosActualizar.activo(),
                datosActualizar.perfil()
        );

        usuario.setContrasena(bCryptPasswordEncoder.encode(usuario.getContrasena()));

        return new DTO_RespuestaUsuario(usuario);

    }
}
