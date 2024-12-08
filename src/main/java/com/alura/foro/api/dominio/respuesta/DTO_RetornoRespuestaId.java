package com.alura.foro.api.dominio.respuesta;

import com.alura.foro.api.dominio.topico.DTO_RespuestaTopico;
import com.alura.foro.api.dominio.usuario.DTO_RespuestaUsuario;

// Record que representa los datos de retorno de una respuesta por su ID
public record DTO_RetornoRespuestaId(
        String mensaje, // Mensaje de la respuesta
        DTO_RespuestaTopico topico, // Datos de respuesta del tópico asociado
        String fechaCreacion, // Fecha de creación de la respuesta en formato string
        DTO_RespuestaUsuario autor, // Datos de respuesta del autor de la respuesta
        String solucion) { // Indica si la respuesta es marcada como solución o no

    /**
     * Constructor que acepta un objeto Respuesta y extrae los datos necesarios.
     *
     * @param respuesta Objeto Respuesta del cual se extraen los datos.
     */
    public DTO_RetornoRespuestaId(Respuesta respuesta) {
        this(respuesta.getMensaje(), // Asigna el mensaje de la respuesta
                new DTO_RespuestaTopico(respuesta.getTopico()), // Crea datos de respuesta del tópico asociado
                respuesta.getFechaCreacion().toString(), // Convierte la fecha de creación a string
                new DTO_RespuestaUsuario(respuesta.getAutor()), // Crea datos de respuesta del autor
                respuesta.getSolucion().toString()); // Convierte el estado de solución a string
    }
}