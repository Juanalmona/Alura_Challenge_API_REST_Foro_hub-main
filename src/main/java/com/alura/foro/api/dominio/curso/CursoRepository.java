package com.alura.foro.api.dominio.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz que define las operaciones de base de datos para la entidad Curso.
 * Extiende JpaRepository para heredar métodos CRUD estándar.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

    /**
     * Método para buscar cursos activos paginados.
     *
     * @param paginacion Objeto Pageable que define la paginación de resultados.
     * @return Página de cursos activos según la paginación especificada.
     */
    Page<Curso> findByActivoTrue(Pageable paginacion);
}