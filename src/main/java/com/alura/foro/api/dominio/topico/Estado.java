package com.alura.foro.api.dominio.topico;


// Enumeración que representa el estado de un tema en el foro
public enum Estado {

    NO_RESPONDIDO,  // Estado cuando el tema no ha recibido respuestas
    NO_SOLUCIONADO, // Estado cuando el tema tiene respuestas pero no está marcado como solucionado
    SOLUCIONADO,    // Estado cuando el tema ha sido marcado como solucionado
    CERRADO;        // Estado cuando el tema ha sido cerrado (generalmente no se pueden añadir más respuestas)

}