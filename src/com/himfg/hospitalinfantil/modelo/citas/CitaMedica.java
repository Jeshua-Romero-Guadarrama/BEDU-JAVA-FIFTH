package com.himfg.hospitalinfantil.modelo.citas;

import com.himfg.hospitalinfantil.excepciones.ValidacionDominioException;
import com.himfg.hospitalinfantil.modelo.comun.EntidadBase;
import com.himfg.hospitalinfantil.modelo.personas.Medico;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;

import java.time.LocalDateTime;

/**
 * Representa una cita medica entre un paciente y un medico.
 */
public class CitaMedica extends EntidadBase {

    private final Paciente paciente;
    private final Medico medico;
    private LocalDateTime fechaHora;
    private EstadoCita estado = EstadoCita.PROGRAMADA;
    private String motivo;

    public CitaMedica(Paciente paciente,
                      Medico medico,
                      LocalDateTime fechaHora,
                      String motivo) {
        if (paciente == null || medico == null) {
            throw new ValidacionDominioException("Paciente y medico son obligatorios para una cita.");
        }
        this.paciente = paciente;
        this.medico = medico;
        setFechaHora(fechaHora);
        setMotivo(motivo);
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new ValidacionDominioException("La fecha y hora de la cita es obligatoria.");
        }
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new ValidacionDominioException("La cita no puede programarse en el pasado.");
        }
        this.fechaHora = fechaHora;
        marcarActualizado();
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoCita nuevoEstado) {
        if (nuevoEstado == null) {
            throw new ValidacionDominioException("El estado de la cita es obligatorio.");
        }
        this.estado = nuevoEstado;
        marcarActualizado();
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new ValidacionDominioException("El motivo de la cita es obligatorio.");
        }
        this.motivo = motivo.trim();
        marcarActualizado();
    }
}
