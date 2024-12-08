package com.alura.foro.api.controller;

import com.alura.foro.api.dominio.respuesta.*;
import com.alura.foro.api.dominio.topico.Estado;
import com.alura.foro.api.dominio.respuesta.Respuesta;
import com.alura.foro.api.dominio.topico.Topico;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.respuesta.RespuestaRepository;
import com.alura.foro.api.dominio.topico.TopicoRepository;
import com.alura.foro.api.dominio.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuestas", description = "Crear, Consultar, Actualizar y Eliminar Respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra una respuesta en la base de datos.
     * Solo puede ser realizada por usuarios con ROLE_ADMIN o ROLE_USER.
     *
     * @param datosRegistro Los datos de registro de la respuesta.
     * @param uri           Builder para la URI de respuesta.
     * @return ResponseEntity con los datos de la respuesta registrada y la URI de la respuesta creada.
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Registra una respuesta en la base de datos ")

    public ResponseEntity<DTO_RetornoRespuesta> registrar(
            @RequestBody @Valid DTO_RegistroRespuesta datosRegistro,
            UriComponentsBuilder uri) {

        // Obtiene el tópico asociado a la respuesta
        Topico topico = topicoRepository.getReferenceById(datosRegistro.topicoId());

        // Verifica si el tópico está cerrado antes de permitir registrar una respuesta
        if (topico.getEstado() == Estado.CERRADO) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Obtiene el autor de la respuesta
        Usuario autor = usuarioRepository.getReferenceById(datosRegistro.autorId());

        // Guarda la respuesta en la base de datos
        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistro, topico, autor));

        // Agrega la respuesta al tópico
        topico.agregarRespuesta(respuesta);

        // Construye la URL de la ubicación de la nueva respuesta registrada
        URI url = uri.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        // Devuelve una respuesta con código 201 Created y los datos de la respuesta registrada
        return ResponseEntity.created(url).body(new DTO_RetornoRespuesta(respuesta));
    }

    /**
     * Obtiene todas las respuestas activas en la base de datos paginadas.
     *
     * @param paginacion La configuración de paginación para la consulta.
     * @return ResponseEntity con una página de respuestas listadas.
     */
    @GetMapping
    @Operation(
            summary = "Obtener todas las respuestas en la base de datos",
            description = "")
    public ResponseEntity<Page<DTO_ListadoRespuesta>> listar(
            @PageableDefault(size = 10) Pageable paginacion) {
        // Obtiene una página de respuestas activas usando el repositorio
        Page<DTO_ListadoRespuesta> paginaRespuestas = respuestaRepository.findByActivoTrue(paginacion)
                .map(DTO_ListadoRespuesta::new);

        // Devuelve una respuesta con la página de respuestas activas
        return ResponseEntity.ok(paginaRespuestas);
    }

    /**
     * Consulta una respuesta por su ID en la base de datos.
     *
     * @param id El ID de la respuesta a consultar.
     * @return ResponseEntity con los datos de la respuesta consultada.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Consulta una respuesta por ID en la base de datos",
            description = "")
    public ResponseEntity<DTO_RetornoRespuestaId> retornaDatos(
            @PathVariable Long id) {
        // Obtiene la respuesta por su ID para consultar sus datos
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        // Devuelve una respuesta con los datos de la respuesta encontrada
        return ResponseEntity.ok(new DTO_RetornoRespuestaId(respuesta));
    }

    /**
     * Actualiza una respuesta en la base de datos.
     * Solo puede ser realizada por usuarios con ROLE_ADMIN o ROLE_USER.
     *
     * @param datosActualizar Los datos actualizados de la respuesta.
     * @return ResponseEntity con los datos de la respuesta actualizada.
     */
    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza las respuestas en la base de datos",
            description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity<DTO_RetornoRespuesta> actualizar(
            @RequestBody @Valid DTO_ActualizarRespuesta datosActualizar) {
        // Obtiene la respuesta por su ID para actualizarla
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizar.id());

        // Obtiene el tópico y el autor asociados a la respuesta
        Topico topico = topicoRepository.getReferenceById(datosActualizar.topicoId());
        Usuario autor = usuarioRepository.getReferenceById(datosActualizar.autorId());

        // Actualiza los datos de la respuesta
        respuesta.actualizarDatos(datosActualizar, topico, autor);

        // Devuelve una respuesta con los datos actualizados de la respuesta
        return ResponseEntity.ok(new DTO_RetornoRespuesta(respuesta));
    }

    /**
     * Elimina una respuesta por su ID en la base de datos
     *
     * @param id El ID de la respuesta a eliminar.
     * @return ResponseEntity indicando el éxito de la operación.
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Desactiva Respuesta por su ID para eliminarlo lógicamente. Solo perfil ADMIN",
            description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {
        // Obtiene la respuesta por su ID para eliminarla lógicamente
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        // Desactiva la respuesta llamando al método desactivarRespuesta
        respuesta.desactivarRespuesta();

        // Devuelve una respuesta sin contenido para indicar éxito
        return ResponseEntity.noContent().build();
    }
}