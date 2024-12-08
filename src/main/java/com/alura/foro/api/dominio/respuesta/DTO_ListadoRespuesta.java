package com.alura.foro.api.dominio.respuesta;

// Record que representa los datos de listado de una respuesta
public record DTO_ListadoRespuesta(
        Long id,         // Identificador único de la respuesta
        String mensaje,  // Mensaje de la respuesta
        String topico,   // Título del tópico al que pertenece la respuesta
        String autor) {  // Nombre del autor de la respuesta

    /**
     * Constructor que acepta un objeto Respuesta y extrae los datos necesarios para el listado.
     *
     * @param respuesta Objeto de tipo Respuesta del cual se extraen los datos.
     */
    public DTO_ListadoRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),                         // ID de la respuesta
                respuesta.getMensaje(),                    // Mensaje de la respuesta
                respuesta.getTopico().getTitulo(),         // Título del tópico asociado a la respuesta
                respuesta.getAutor().getNombre()           // Nombre del autor de la respuesta
        );
    }
}