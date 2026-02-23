package com.himfg.hospitalinfantil.excepciones;

/**
 * Se lanza cuando se viola una regla de negocio o validacion del dominio.
 */
public class ValidacionDominioException extends RuntimeException {

    public ValidacionDominioException(String mensaje) {
        super(mensaje);
    }
}
