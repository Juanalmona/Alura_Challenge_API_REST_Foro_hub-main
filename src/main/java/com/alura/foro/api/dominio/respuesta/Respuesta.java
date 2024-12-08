package com.alura.foro.api.dominio.respuesta;

import com.alura.foro.api.dominio.topico.Estado;
import com.alura.foro.api.dominio.topico.Topico;
import com.alura.foro.api.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Clase `Respuesta` que representa una respuesta a un `Topico` en el foro.
 * Utiliza anotaciones JPA para mapear la clase como una entidad en la base de datos.
 */
@Table(name = "respuestas") // Define el nombre de la tabla en la base de datos
@Entity(name = "Respuesta") // Define que esta clase es una entidad JPA llamada "Respuesta"
@Getter // Genera getters automáticos para todos los campos
@AllArgsConstructor // Constructor con todos los argumentos generado automáticamente
@NoArgsConstructor // Constructor sin argumentos generado automáticamente
@EqualsAndHashCode(of = "id") // Genera equals y hashCode usando solo el campo "id"
public class Respuesta {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de valores de clave primaria
    private Long id; // Identificador único de la respuesta

    private String mensaje; // Mensaje de la respuesta

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Topico, cargado de forma perezosa (lazy)
    @JoinColumn(name = "topico_id") // Nombre de la columna en la tabla respuestas que referencia a Topico
    private Topico topico; // Topico al que pertenece la respuesta

    @Column(name = "fecha_creacion") // Nombre de la columna en la tabla respuestas para la fecha de creación
    private LocalDateTime fechaCreacion = LocalDateTime.now(); // Fecha de creación de la respuesta (por defecto, la actual)

    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Usuario, cargado de forma perezosa (lazy)
    @JoinColumn(name = "autor_id") // Nombre de la columna en la tabla respuestas que referencia a Usuario
    private Usuario autor; // Usuario que creó la respuesta

    private Boolean solucion = false; // Indica si la respuesta es marcada como solución o no

    private Boolean activo; // Indica si la respuesta está activa o no

    /**
     * Constructor para inicializar una respuesta a partir de datos de registro.
     *
     * @param datos   Datos de registro de la respuesta.
     * @param topico  Topico al que pertenece la respuesta.
     * @param autor   Usuario que crea la respuesta.
     */
    public Respuesta(DTO_RegistroRespuesta datos, Topico topico, Usuario autor) {
        this.activo = true; // Por defecto, la respuesta se inicializa como activa
        this.mensaje = datos.mensaje(); // Asigna el mensaje de la respuesta desde los datos de registro
        this.topico = topico; // Asigna el topico de la respuesta
        this.autor = autor; // Asigna el autor de la respuesta
        this.solucion = datos.solucion(); // Marca la respuesta como solución si así se indica en los datos

        // Actualiza el estado del topico según si la respuesta es marcada como solución
        if (datos.solucion()) {
            this.topico.setEstado(Estado.SOLUCIONADO);
        } else {
            this.topico.setEstado(Estado.NO_SOLUCIONADO);
        }
    }

    /**
     * Método para actualizar los datos de la respuesta.
     *
     * @param datosActualizar Datos actualizados de la respuesta.
     * @param topico          Nuevo topico asociado a la respuesta (opcional).
     * @param autor           Nuevo autor asociado a la respuesta (opcional).
     */
    public void actualizarDatos(DTO_ActualizarRespuesta datosActualizar, Topico topico, Usuario autor) {
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje(); // Actualiza el mensaje si se proporciona uno nuevo
        }
        if (topico != null) {
            this.topico = topico; // Actualiza el topico si se proporciona uno nuevo
        }
        if (autor != null) {
            this.autor = autor; // Actualiza el autor si se proporciona uno nuevo
        }
        if (datosActualizar.solucion() != this.solucion) {
            this.solucion = datosActualizar.solucion(); // Actualiza el estado de solución si cambia
        }
    }

    /**
     * Método para desactivar una respuesta.
     * Marca la respuesta como inactiva.
     */
    public void desactivarRespuesta() {
        this.activo = false; // Cambia el estado de activo a false para marcar la respuesta como inactiva
    }
}