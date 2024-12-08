package com.alura.foro.api.dominio.curso;

// Record que representa los datos de listado de un curso
public record    DTO_ListadoCurso(
        Long id,                // Identificador único del curso
        String nombre,          // Nombre del curso
        String categoria) {     // Categoría del curso

    /**
     * Constructor que acepta un objeto Curso y extrae los datos necesarios para
     * inicializar los campos del record.
     *
     * @param curso Objeto Curso del cual se extraen los datos de id, nombre y categoría.
     */
    public DTO_ListadoCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}