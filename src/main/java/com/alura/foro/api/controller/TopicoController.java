package com.alura.foro.api.controller;

import com.alura.foro.api.dominio.topico.*;
import com.alura.foro.api.dominio.curso.Curso;
import com.alura.foro.api.dominio.topico.Topico;
import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.curso.CursoRepository;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con los tópicos del foro.
 */
@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topicos", description = "Crear, Consultar, Actualizar y Eliminar Topicos")
@Validated
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Registra un nuevo tópico en la base de datos.
     *
     * @param datosRegistro Los datos de registro del tópico.
     * @param uri           Builder para la URI de respuesta.
     * @return ResponseEntity con los datos del tópico registrado y la URI de la respuesta creada.
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Registra un tópico en la base de datos")
    public ResponseEntity<DTO_RespuestaTopico> registrar(
            @RequestBody @Valid DTO_RegistroTopico datosRegistro,
            UriComponentsBuilder uri) {
        Usuario autor = usuarioRepository.getReferenceById(datosRegistro.autorId());
        Curso curso = cursoRepository.getReferenceById(datosRegistro.cursoId());
        Topico topico = topicoRepository.save(new Topico(datosRegistro, autor, curso));
        DTO_RespuestaTopico datosRespuesta = new DTO_RespuestaTopico(topico);
        URI url = uri.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    /**
     * Obtiene todos los tópicos activos en la base de datos paginados.
     *
     * @param paginacion La configuración de paginación para la consulta.
     * @return ResponseEntity con una página de tópicos listados.
     */
    @GetMapping
    @Operation(summary = "Consulta todos los tópicos en la base de datos")
    public ResponseEntity<Page<DTO_ListadoTopico>> listar(
            @PageableDefault(size = 10) Pageable paginacion) {
        Page<Topico> paginaTopicos = topicoRepository.findByActivoTrue(paginacion);
        Page<DTO_ListadoTopico> paginaDTO = paginaTopicos.map(DTO_ListadoTopico::new);
        return ResponseEntity.ok(paginaDTO);
    }


    /**
     * Consulta un tópico por su ID en la base de datos.
     *
     * @param id El ID del tópico a consultar.
     * @return ResponseEntity con los datos del tópico consultado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Consulta un tópico por ID en la base de datos")
    public ResponseEntity<DTO_RespuestaTopicoId> retornaDatos(
            @PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        DTO_RespuestaTopicoId datosRespuesta = new DTO_RespuestaTopicoId(topico);
        return ResponseEntity.ok(datosRespuesta);
    }



    /**
     * Activa un tópico en la base de datos.
     *
     *  @return ResponseEntity indicando el éxito de la operación.
     */
    @GetMapping("/activa/{id}")
    @Transactional
    @Operation(summary = "Activa un tópico existente en la base de datos, Solo perfil ADMIN",
    description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity activarTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.activartopico();
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza un tópico en la base de datos.
     *
     * @param datosActualizar Los datos actualizados del tópico.
     * @return ResponseEntity con los datos del tópico actualizado.
     */
    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza un tópico en la base de datos")
    public ResponseEntity<DTO_RespuestaTopico> actualizar(
            @RequestBody @Valid DTO_ActualizarTopico datosActualizar) {
        Usuario autor = usuarioRepository.getReferenceById(datosActualizar.autorId());
        Curso curso = cursoRepository.getReferenceById(datosActualizar.cursoId());
        Topico topico = topicoRepository.getReferenceById(datosActualizar.id());
        topico.actualizarDatos(datosActualizar, autor, curso);
        DTO_RespuestaTopico datosRespuesta = new DTO_RespuestaTopico(topico);
        return ResponseEntity.ok(datosRespuesta);
    }

    /**
     * Elimina un tópico por su ID logicamente.
     * Lo cierra y lo desactiva
     * @param id El ID del tópico a eliminar.
     * @return ResponseEntity indicando el éxito de la operación.
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Desactiva y cierra un tópico por ID en la base de datos (Elimina lógicamente). Solo perfil ADMIN",
            description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivartopico();
        topico.cerrarTopico(); // Cambia el estado del tópico a cerrado
        return ResponseEntity.noContent().build();
        // RETORNA 204  No Content
    }

    /**
     * Elimina un tópico por su ID en la base de datos.
     *
     * @param id El ID del tópico a eliminar.
     * @return ResponseEntity indicando el éxito de la operación.
     */
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    @Operation(summary = "Elimina un tópico por ID en la base de datos. Solo perfil ADMIN",
              description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topicoRepository.deleteById(topico.getId());
            return ResponseEntity.ok().body("El topico se eliminó exitosamente");
        }
        return ResponseEntity.noContent().build();
        // RETORNA 204  No Content
    }

    // Método para listar tópicos por usuario (autor)
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Consulta tópicos por usuario (autor)")
    public ResponseEntity<List<DTO_ListadoTopico>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Topico> topicos = topicoRepository.findByUsuarioId(usuarioId);
        List<DTO_ListadoTopico> datosTopicos = topicos.stream().map(DTO_ListadoTopico::new).toList();
        return ResponseEntity.ok(datosTopicos);
    }

    // Método para listar tópicos por curso
    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Consulta tópicos por curso")
    public ResponseEntity<List<DTO_ListadoTopico>> listarPorCurso(@PathVariable Long cursoId) {
        List<Topico> topicos = topicoRepository.findByCursoId(cursoId);
        List<DTO_ListadoTopico> datosTopicos = topicos.stream().map(DTO_ListadoTopico::new).toList();
        return ResponseEntity.ok(datosTopicos);
    }

    // Método adicional para listar tópicos por estado
    @GetMapping("/estado")
    @Operation(summary = "Consulta tópicos por estado")
    public ResponseEntity<List<DTO_ListadoTopico>> listarPorEstado(@RequestParam("estado") Estado estado) {
        List<Topico> topicos = topicoRepository.findByEstado(estado);
        List<DTO_ListadoTopico> datosTopicos = topicos.stream().map(DTO_ListadoTopico::new).toList();
        return ResponseEntity.ok(datosTopicos);
    }

    // Método para listar tópicos por fecha de creación
    @GetMapping("/fecha")
    @Operation(summary = "Consulta tópicos por fecha de creación")
    public ResponseEntity<List<DTO_ListadoTopico>> listarPorFecha(
            @RequestParam("fechaInicio") LocalDateTime fechaInicio,
            @RequestParam("fechaFin") LocalDateTime fechaFin) {
        List<Topico> topicos = topicoRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
        List<DTO_ListadoTopico> datosTopicos = topicos.stream().map(DTO_ListadoTopico::new).toList();
        return ResponseEntity.ok(datosTopicos);
    }

    // Método para listar tópicos con respuestas
    @GetMapping("/con_respuestas")
    @Operation(summary = "Consulta tópicos con respuestas")
    public ResponseEntity<List<DTO_ListadoTopico>> listarConRespuestas() {
        List<Topico> topicos = topicoRepository.findWithRespuestas();
        List<DTO_ListadoTopico> datosTopicos = topicos.stream().map(DTO_ListadoTopico::new).toList();
        return ResponseEntity.ok(datosTopicos);
    }



}