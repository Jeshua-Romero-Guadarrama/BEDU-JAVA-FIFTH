package com.himfg.hospitalinfantil.modelo.encuestas;

import com.himfg.hospitalinfantil.modelo.comun.EntidadBase;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una encuesta de salud aplicada a un paciente en una fecha especifica.
 */
public class EncuestaAplicada extends EntidadBase {

    private final Paciente paciente;
    private final EncuestaSalud encuestaOriginal;
    private final LocalDateTime fechaAplicacion;
    private final List<Respuesta> respuestas = new ArrayList<>();

    public EncuestaAplicada(Paciente paciente, EncuestaSalud encuestaOriginal) {
        if (paciente == null || encuestaOriginal == null) {
            throw new IllegalArgumentException("Paciente y encuesta son obligatorios.");
        }
        this.paciente = paciente;
        this.encuestaOriginal = encuestaOriginal;
        this.fechaAplicacion = LocalDateTime.now();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public EncuestaSalud getEncuestaOriginal() {
        return encuestaOriginal;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public List<Respuesta> getRespuestas() {
        return Collections.unmodifiableList(respuestas);
    }

    public void agregarRespuesta(Respuesta respuesta) {
        if (respuesta == null) {
            throw new IllegalArgumentException("La respuesta no puede ser nula.");
        }
        respuestas.add(respuesta);
        marcarActualizado();
    }
}
