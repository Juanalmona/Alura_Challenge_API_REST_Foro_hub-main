package com.alura.foro.api.dominio.respuesta;

// Record que representa los datos de retorno de una respuesta
public record DTO_RetornoRespuesta(String mensaje, String topico, String autor) {

    /**
     * Constructor que acepta un objeto Respuesta y extrae los datos necesarios.
     *
     * @param respuesta La respuesta de la cual se extraen los datos.
     */
    public DTO_RetornoRespuesta(Respuesta respuesta) {
        this(respuesta.getMensaje(),           // Obtiene el mensaje de la respuesta
                respuesta.getTopico().getTitulo(), // Obtiene el título del topico asociado a la respuesta
                respuesta.getAutor().getNombre()); // Obtiene el nombre del autor de la respuesta
    }
}
