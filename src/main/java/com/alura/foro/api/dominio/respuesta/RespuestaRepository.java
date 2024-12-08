package com.alura.foro.api.dominio.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaz que gestiona la interacción con la base de datos para la entidad Respuesta
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    /**
     * Método para buscar respuestas activas paginadas.
     *
     * @param paginacion Configuración de paginación para la búsqueda.
     * @return Página de respuestas activas.
     */
    Page<Respuesta> findByActivoTrue(Pageable paginacion);
}
