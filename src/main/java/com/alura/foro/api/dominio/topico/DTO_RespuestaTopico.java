package com.alura.foro.api.dominio.topico;

/**
 * Record que representa los datos resumidos de respuesta de un tema (topico) en el foro.
 * Contiene información como el título del tema, mensaje, nombre del autor y nombre del curso asociado.
 */
public record DTO_RespuestaTopico(
        String titulo, // Título del tema
        String mensaje, // Mensaje o contenido del tema
        String autor, // Nombre del autor del tema
        String curso // Nombre del curso asociado al tema
) {

    /**
     * Constructor que convierte un objeto Topico en un objeto DTORespuestaTopico para su respuesta resumida.
     *
     * @param topico El objeto Topico del cual se extraerán los datos.
     */
    public DTO_RespuestaTopico(Topico topico) {
        this(
                topico.getTitulo(), // Asigna el título del tema
                topico.getMensaje(), // Asigna el mensaje del tema
                topico.getAutor().getNombre(), // Asigna el nombre del autor del tema
                topico.getCurso().getNombre() // Asigna el nombre del curso asociado al tema
        );
    }
}