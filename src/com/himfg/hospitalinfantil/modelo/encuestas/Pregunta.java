package com.himfg.hospitalinfantil.modelo.encuestas;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.comun.EntidadBase;

/**
 * Clase abstracta que representa una pregunta dentro de una encuesta.
 */
public abstract class Pregunta extends EntidadBase {

    private final TipoPregunta tipo;
    private String enunciado;

    protected Pregunta(String enunciado, TipoPregunta tipo) {
        this.tipo = tipo;
        setEnunciado(enunciado);
    }

    public TipoPregunta getTipo() {
        return tipo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new ValidacionDominioException("El enunciado de la pregunta es obligatorio.");
        }
        this.enunciado = enunciado.trim();
        marcarActualizado();
    }

    /**
     * Verifica si una respuesta en texto es valida para esta pregunta.
     */
    public abstract boolean esRespuestaValida(String respuestaComoTexto);
}
