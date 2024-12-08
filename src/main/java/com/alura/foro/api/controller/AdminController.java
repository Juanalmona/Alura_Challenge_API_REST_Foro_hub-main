package com.alura.foro.api.controller;

import com.alura.foro.api.dominio.usuario.*;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import com.alura.foro.api.dominio.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearer-key")  // Requerimiento de seguridad para todas las operaciones
@Tag(name = "Usuarios - Admin", description = "Crear, Consultar, Actualizar y Eliminar Usuarios")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;  // Servicio para operaciones de usuario

    @Autowired
    private UsuarioRepository usuarioRepository;  // Repositorio de usuarios [SwaggerControllerOrder(3)]

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param datosRegistro Datos del usuario a registrar.
     * @param uri           Builder para construir la URI de respuesta.
     * @return Respuesta con los datos del usuario registrado y la URL de ubicación.
     */
    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Registra un usuario en la base de datos")
    public ResponseEntity<DTO_RespuestaUsuario> registrar(
            @RequestBody @Valid DTO_RegistroUsuario datosRegistro,
            UriComponentsBuilder uri) {

        // Llama al servicio para crear un usuario y obtener los datos de respuesta
        DTO_RespuestaUsuario datosRespuesta = usuarioService.crearUsuario(datosRegistro);

        // Construye la URL de la ubicación del nuevo usuario registrado
        URI url = uri.path("/admin/{id}").buildAndExpand(datosRespuesta.id()).toUri();

        // Devuelve una respuesta con código 201 Created y los datos del usuario registrado
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    /**
     * Endpoint para listar todos los usuarios activos.
     *
     * @param paginacion Paginación de resultados.
     * @return Lista paginada de usuarios activos.
     */
    @GetMapping("/usuarios")
    @Operation(summary = "Consulta todos los usuarios en la base de datos")
    public ResponseEntity<Page<DTO_ListadoUsuario>> listar(
            @PageableDefault(size = 10) Pageable paginacion) {

        // Obtiene una página de usuarios activos usando el repositorio
        Page<DTO_ListadoUsuario> paginaUsuarios = usuarioRepository.findByActivoTrue(paginacion)
                .map(DTO_ListadoUsuario::new);

        // Devuelve una respuesta con la página de usuarios activos
        return ResponseEntity.ok(paginaUsuarios);
    }

    /**
     * Endpoint para consultar los datos de un usuario por su ID.
     *
     * @param id ID del usuario a consultar.
     * @return Respuesta con los datos del usuario consultado.
     */
    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Consulta un usuario por ID en la base de datos")
    public ResponseEntity<DTO_RespuestaUsuarioId> retornaDatos(
            @PathVariable Long id) {

        // Busca un usuario por su ID usando el repositorio
        Usuario usuario = usuarioRepository.getReferenceById(id);

        // Devuelve una respuesta con los datos detallados del usuario consultado
        return ResponseEntity.ok(new DTO_RespuestaUsuarioId(usuario));
    }

    /**
     * Endpoint para actualizar los datos de un usuario por su ID.
     *
     * @param datosActualizar Datos actualizados del usuario.
     * @return Respuesta con los datos actualizados del usuario.
     */
    @PatchMapping("/usuarios")
    @Transactional
    @Operation(summary = "Actualiza el usuario en la base de datos")
    public ResponseEntity DatosRespuestaUsuario(
            @RequestBody @Valid DTO_ActualizarUsuario datosActualizar) {

        // Utiliza el servicio para actualizar los datos del usuario
        DTO_RespuestaUsuario datosRespuesta = usuarioService.actualizarUsuarioAdmin(datosActualizar);

        // Devuelve una respuesta con los datos actualizados del usuario
        return ResponseEntity.ok(datosRespuesta);
    }

    /**
     * Endpoint para desactivar un usuario por su ID.
     *
     * @param id ID del  usuario a desactivar.
     * @return Respuesta indicando el éxito de la desactivación.
     */
    @DeleteMapping("/usuarios/{id}")
    @Transactional
    @Operation(summary = "Desactiva un usuario por su ID para eliminarlo lógicamente",
            description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        // Busca un usuario por su ID usando el repositorio
        Usuario usuario = usuarioRepository.getReferenceById(id);

        // Desactiva el usuario llamando al método desactivarUsuario
        usuario.desactivarUsuario();

        // Devuelve una respuesta sin contenido para indicar éxito
        return ResponseEntity.noContent().build();
    }
}