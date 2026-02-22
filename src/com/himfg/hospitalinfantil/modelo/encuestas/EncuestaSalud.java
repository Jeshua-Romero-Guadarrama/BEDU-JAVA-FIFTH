package com.himfg.hospitalinfantil.modelo.encuestas;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.comun.EntidadBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una bateria de preguntas de salud aplicada en el hospital.
 */
public class EncuestaSalud extends EntidadBase {

    private String nombre;
    private String descripcion;
    private final List<Pregunta> preguntas = new ArrayList<>();

    public EncuestaSalud(String nombre, String descripcion) {
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionDominioException("El nombre de la encuesta es obligatorio.");
        }
        this.nombre = nombre.trim();
        marcarActualizado();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new ValidacionDominioException("La descripcion de la encuesta es obligatoria.");
        }
        this.descripcion = descripcion.trim();
        marcarActualizado();
    }

    public List<Pregunta> getPreguntas() {
        return Collections.unmodifiableList(preguntas);
    }

    public void agregarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            throw new ValidacionDominioException("La pregunta no puede ser nula.");
        }
        preguntas.add(pregunta);
        marcarActualizado();
    }
}
