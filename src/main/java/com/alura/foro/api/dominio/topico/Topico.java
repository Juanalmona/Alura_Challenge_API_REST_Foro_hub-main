package com.alura.foro.api.dominio.topico;

import com.alura.foro.api.dominio.usuario.Usuario;
import com.alura.foro.api.dominio.curso.Curso;
import com.alura.foro.api.dominio.respuesta.Respuesta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un tema de discusión en el foro.
 */
@Table(name= "topicos")  // Define el nombre de la tabla en la base de datos
@Entity(name="Topico")  // Define el nombre de la entidad en JPA
@Getter  // Genera getters automáticos para todos los campos
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor  // Genera un constructor con todos los argumentos
@EqualsAndHashCode(of = "id")  // Genera equals() y hashCode() usando solo el campo "id"
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del tópico

    private String titulo;  // Título del tópico

    private String mensaje;  // Mensaje o contenido del tópico

    private Boolean activo;  // Estado de activo/inactivo del tópico

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();  // Fecha de creación del tópico

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.NO_RESPONDIDO;  // Estado del tópico, inicialmente NO_RESPONDIDO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;  // Autor del tópico, relación Many-to-One con Usuario

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;  // Curso al que pertenece el tópico, relación Many-to-One con Curso

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topico_id", referencedColumnName = "id")
    private List<Respuesta> respuestas = new ArrayList<>();  // Lista de respuestas asociadas al tópico

    /**
     * Constructor para crear un nuevo tópico a partir de datos de registro.
     *
     * @param datos Los datos de registro del tópico.
     * @param autor El usuario que creó el tópico.
     * @param curso El curso al que pertenece el tópico.
     */
    public Topico(DTO_RegistroTopico datos, Usuario autor, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = autor;
        this.curso = curso;
        this.activo = true;
    }

    /**
     * Método para actualizar los datos de un tópico.
     *
     * @param datosActualizar Los datos actualizados del tópico.
     * @param autor El nuevo autor del tópico.
     * @param curso El nuevo curso al que pertenece el tópico.
     */
    public void actualizarDatos(DTO_ActualizarTopico datosActualizar, Usuario autor, Curso curso) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo();
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
        if (datosActualizar.estado() != datosActualizar.estado()) {
            this.estado = datosActualizar.estado();
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (curso != null) {
            this.curso = curso;
        }
    }

    /**
     * Método para agregar una respuesta a la lista de respuestas del tópico.
     *
     * @param respuesta La respuesta a agregar.
     */
    public void agregarRespuesta(Respuesta respuesta) {
        this.respuestas.add(respuesta);
        if (respuesta.getSolucion()) {
            this.estado = Estado.SOLUCIONADO;  // Si la respuesta es marcada como solución, el estado del tópico se actualiza a SOLUCIONADO
        } else {
            this.estado = Estado.NO_SOLUCIONADO;  // Si no es solución, se marca como NO_SOLUCIONADO
        }
    }

    /**
     * Método para desactivar un tópico.
     */
    public void desactivartopico() {
        this.activo = false;
    }

    /**
     * Método para activar un tópico.
     */
    public void activartopico() {
        this.activo = true;
    }

    /**
     * Método para cerrar un tópico, cambiando su estado a CERRADO.
     */
    public void cerrarTopico() {
        this.estado = Estado.CERRADO;
    }

    /**
     * Setter para actualizar el estado del tópico.
     *
     * @param estado El nuevo estado del tópico.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }


}