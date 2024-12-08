package com.alura.foro.api.dominio.topico;

/**
 * Record que encapsula los datos necesarios para listar un tema (topico) en formato resumido.
 * Incluye el ID, título, mensaje, nombre del autor y nombre del curso asociado.
 */
public record DTO_ListadoTopico(
        Long id, // Identificador único del tema
        String titulo, // Título del tema
        String mensaje, // Mensaje o contenido del tema
        String autor, // Nombre del autor del tema
        String curso // Nombre del curso asociado al tema

) {

    /**
     * Constructor que acepta un objeto Topico y extrae los datos necesarios para el listado.
     * @param topico Objeto Topico del cual se extraerán los datos.
     */
    public DTO_ListadoTopico(Topico topico) {
        this(topico.getId(), // Asigna el ID del tema
                topico.getTitulo(), // Asigna el título del tema
                topico.getMensaje(), // Asigna el mensaje del tema
                topico.getAutor().getNombre(), // Obtiene el nombre del autor del tema
                topico.getCurso().getNombre()); // Obtiene el nombre del curso asociado al tema
    }
}
