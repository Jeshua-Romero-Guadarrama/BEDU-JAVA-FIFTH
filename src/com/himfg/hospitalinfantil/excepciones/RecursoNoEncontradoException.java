package com.himfg.hospitalinfantil.excepciones;
/**
 * Se lanza cuando no se encuentra un recurso esperado.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
