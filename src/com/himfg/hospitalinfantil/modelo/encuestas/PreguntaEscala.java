package com.himfg.hospitalinfantil.modelo.encuestas;

/**
 * Pregunta con una escala numerica de 1 a 5 (por ejemplo, nivel de dolor).
 */
public class PreguntaEscala extends Pregunta {

    public PreguntaEscala(String enunciado) {
        super(enunciado, TipoPregunta.ESCALA_1_A_5);
    }

    @Override
    public boolean esRespuestaValida(String respuestaComoTexto) {
        try {
            int valor = Integer.parseInt(respuestaComoTexto);
            return valor >= 1 && valor <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
