package com.himfg.hospitalinfantil.modelo.encuestas;

/**
 * Pregunta que espera una respuesta de texto libre no vacia.
 */
public class PreguntaTexto extends Pregunta {

    public PreguntaTexto(String enunciado) {
        super(enunciado, TipoPregunta.TEXTO_LIBRE);
    }

    @Override
    public boolean esRespuestaValida(String respuestaComoTexto) {
        return respuestaComoTexto != null && !respuestaComoTexto.isBlank();
    }
}
