package com.himfg.hospitalinfantil.modelo.encuestas;

/**
 * Representa la respuesta a una pregunta de una encuesta.
 */
public class Respuesta {

    private final Pregunta pregunta;
    private final String valor;

    public Respuesta(Pregunta pregunta, String valor) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta es obligatoria.");
        }
        if (!pregunta.esRespuestaValida(valor)) {
            throw new IllegalArgumentException(
                    "La respuesta '" + valor + "' no es valida para la pregunta: " + pregunta.getEnunciado()
            );
        }
        this.pregunta = pregunta;
        this.valor = valor;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return pregunta.getEnunciado() + " -> " + valor;
    }
}
