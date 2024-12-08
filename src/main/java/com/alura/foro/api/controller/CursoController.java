package com.alura.foro.api.controller;

import com.alura.foro.api.dominio.curso.*;
import com.alura.foro.api.dominio.curso.DTO_RegistroCursos;
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
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos", description = "Crear, Consultar, Actualizar y Eliminar Cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Endpoint para registrar un curso nuevo en la base de datos.
     *
     * @param DTO_RegistroCursos     Datos de  registro del curso.
     * @param uriComponentsBuilder    Builder para construir la URI de respuesta.
     * @return ResponseEntity con los datos del curso registrado y la URI de ubicación.
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Registra un curso en la base de datos")
    public ResponseEntity<DTO_RespuestaCurso> registrarCurso(
            @RequestBody @Valid DTO_RegistroCursos DTO_RegistroCursos,
            UriComponentsBuilder uriComponentsBuilder) {

        // Guarda el curso en la base de datos y obtiene el curso guardado
        Curso curso = cursoRepository.save(new Curso(DTO_RegistroCursos));

        // Construye la URL de la ubicación del nuevo curso registrado
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        // Devuelve una respuesta con código 201 Created y los datos del curso registrado
        return ResponseEntity.created(url).body(new DTO_RespuestaCurso(
                curso.getNombre(), curso.getCategoria()));
    }

    /**
     * Endpoint para obtener todos los cursos activos paginados.
     *
     * @param paginacion    Información de paginación.
     * @return ResponseEntity con la página de cursos activos.
     */
    @GetMapping
    @Operation(
            summary = "Consulta los cursos en la base de datos")
    public ResponseEntity<Page<DTO_ListadoCurso>> listadoCursos(@PageableDefault(size = 9) Pageable paginacion) {
        // Obtiene una página de cursos activos usando el repositorio
        Page<DTO_ListadoCurso> paginaCursos = cursoRepository.findByActivoTrue(paginacion)
                .map(DTO_ListadoCurso::new);

        // Devuelve una respuesta con la página de cursos activos
        return ResponseEntity.ok(paginaCursos);
    }

    /**
     * Endpoint para obtener los datos de un curso por su ID.
     *
     * @param id    ID del curso a consultar.
     * @return ResponseEntity con los datos del curso encontrado.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Consulta los cursos por ID en la base de datos",
            description = "")
    public ResponseEntity<DTO_RespuestaCurso> retornaDatosCurso(
            @PathVariable Long id) {

        // Obtiene el curso por su ID para consultar sus datos
        Curso curso = cursoRepository.getReferenceById(id);

        // Devuelve una respuesta con los datos del curso encontrado
        return ResponseEntity.ok(new DTO_RespuestaCurso(
                curso.getNombre(), curso.getCategoria()));
    }


    /**
     * Endpoint para actualizar los datos de un curso existente.
     *
     * @param DTO_ActualizarCurso  Datos actualizados del curso.
     * @return ResponseEntity con los datos actualizados del curso.
     */
    @PutMapping
    @Transactional
    @Operation(summary = "Actualizar los cursos en la base de datos. Solo perfil ADMIN",
    description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity DatosRespuestaCurso(@RequestBody @Valid DTO_ActualizarCurso DTO_ActualizarCurso) {
        // Obtiene el curso por su ID para actualizarlo
        Curso curso = cursoRepository.getReferenceById(DTO_ActualizarCurso.id());
        // Actualiza los datos del curso
        curso.actualizarCurso(DTO_ActualizarCurso);
        // Devuelve una respuesta con los datos actualizados del curso
        return ResponseEntity.ok(new DTO_RespuestaCurso(curso.getNombre(), curso.getCategoria()));
    }


    /**
     * Endpoint para eliminar lógicamente un curso por su ID.
     *
     * @param id    ID del curso a eliminar.
     * @return ResponseEntity indicando éxito de la operación.
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Desactiva el curso por su ID para eliminarlo lógicamente. Solo perfil ADMIN",
            description = "Para esta solicitud el usuario debe tener derechos de administrador (PERFIL ADMIN)")
    public ResponseEntity<Void> eliminarCurso(
            @PathVariable Long id) {

        // Obtiene el curso por su ID para eliminarlo lógicamente
        Curso curso = cursoRepository.getReferenceById(id);

        // Desactiva el curso llamando al método desactivarCurso
        curso.desactivarCurso();

        // Devuelve una respuesta sin contenido para indicar éxito
        return ResponseEntity.noContent().build();
    }


}