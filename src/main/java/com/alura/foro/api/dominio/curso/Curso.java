package com.alura.foro.api.dominio.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la entidad Curso en la base de datos.
 * Define la estructura y operaciones relacionadas con los cursos.
 */
@Table(name = "cursos") // Define el nombre de la tabla en la base de datos
@Entity(name = "Curso") // Define que esta clase es una entidad JPA llamada "Curso"
@Getter // Genera getters automáticos para todos los campos
@NoArgsConstructor // Constructor sin argumentos generado automáticamente
@AllArgsConstructor // Constructor con todos los argumentos generado automáticamente
@EqualsAndHashCode(of = "id") // Genera equals y hashCode usando solo el campo "id"
public class Curso {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de valores de clave primaria
    private Long id; // Identificador único del curso

    private String nombre; // Nombre del curso

    private String categoria; // Categoría del curso

    private Boolean activo; // Indica si el curso está activo o no

    /**
     * Constructor que inicializa un curso a partir de datos de registro.
     *
     * @param DTO_RegistroCursos Datos de registro para inicializar el curso.
     */
    public Curso(DTO_RegistroCursos DTO_RegistroCursos) {
        this.activo = true; // Por defecto, el curso se inicializa como activo
        this.nombre = DTO_RegistroCursos.nombre(); // Asigna el nombre del curso desde los datos de registro
        this.categoria = DTO_RegistroCursos.categoria(); // Asigna la categoría del curso desde los datos de registro
    }

    /**
     * Método para actualizar los datos del curso según los datos proporcionados.
     *
     * @param DTO_ActualizarCurso Datos actualizados para el curso.
     */
    public void actualizarCurso(DTO_ActualizarCurso DTO_ActualizarCurso) {
        if (DTO_ActualizarCurso.nombre() != null){
            this.nombre = DTO_ActualizarCurso.nombre(); // Actualiza el nombre si se proporciona un nuevo nombre
        }
        // Podrían agregarse más lógica para actualizar otros campos del curso según sea necesario
    }

    /**
     * Método para desactivar un curso (marcar como inactivo).
     * Cambia el estado del curso a inactivo.
     */
    public void desactivarCurso() {
        this.activo = false; // Cambia el estado del curso a inactivo
    }

}