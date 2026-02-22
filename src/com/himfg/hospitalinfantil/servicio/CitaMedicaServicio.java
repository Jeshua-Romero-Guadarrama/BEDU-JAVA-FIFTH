package com.himfg.hospitalinfantil.servicio;

import com.himfg.hospitalinfantil.excepciones.RecursoNoEncontradoException;
import com.himfg.hospitalinfantil.modelo.citas.CitaMedica;
import com.himfg.hospitalinfantil.modelo.citas.EstadoCita;
import com.himfg.hospitalinfantil.modelo.personas.Medico;
import com.himfg.hospitalinfantil.modelo.personas.Paciente;
import com.himfg.hospitalinfantil.repositorio.CitaMedicaRepositorio;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para programar y administrar citas medicas.
 */
public class CitaMedicaServicio {

    private final CitaMedicaRepositorio citaRepositorio;

    public CitaMedicaServicio(CitaMedicaRepositorio citaRepositorio) {
        this.citaRepositorio = citaRepositorio;
    }

    public CitaMedica programarCita(Paciente paciente,
                                    Medico medico,
                                    LocalDateTime fechaHora,
                                    String motivo) {
        CitaMedica cita = new CitaMedica(paciente, medico, fechaHora, motivo);
        return citaRepositorio.guardar(cita);
    }

    public CitaMedica obtenerPorId(Long id) {
        return citaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro la cita con id " + id));
    }

    public List<CitaMedica> listarCitas() {
        return citaRepositorio.buscarTodas();
    }

    public List<CitaMedica> listarCitasPorPaciente(Long pacienteId) {
        return citaRepositorio.buscarPorPaciente(pacienteId);
    }

    public CitaMedica cambiarEstado(Long id, EstadoCita nuevoEstado) {
        CitaMedica cita = obtenerPorId(id);
        cita.cambiarEstado(nuevoEstado);
        citaRepositorio.guardar(cita);
        return cita;
    }

    public CitaMedica reprogramarCita(Long id, LocalDateTime nuevaFechaHora) {
        CitaMedica cita = obtenerPorId(id);
        cita.setFechaHora(nuevaFechaHora);
        citaRepositorio.guardar(cita);
        return cita;
    }
}
