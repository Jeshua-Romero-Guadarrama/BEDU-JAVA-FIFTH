package com.himfg.hospitalinfantil.servicio;

import com.himfg.hospitalinfantil.excepciones.RecursoNoEncontradoException;
import com.himfg.hospitalinfantil.modelo.personas.EspecialidadMedica;
import com.himfg.hospitalinfantil.modelo.personas.Medico;
import com.himfg.hospitalinfantil.repositorio.MedicoRepositorio;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para gestionar medicos.
 */
public class MedicoServicio {

    private final MedicoRepositorio medicoRepositorio;

    public MedicoServicio(MedicoRepositorio medicoRepositorio) {
        this.medicoRepositorio = medicoRepositorio;
    }

    public Medico registrarMedico(String nombreCompleto,
                                  LocalDate fechaNacimiento,
                                  EspecialidadMedica especialidad,
                                  String cedulaProfesional) {
        Medico medico = new Medico(nombreCompleto, fechaNacimiento, especialidad, cedulaProfesional);
        return medicoRepositorio.guardar(medico);
    }

    public Medico obtenerPorId(Long id) {
        return medicoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro el medico con id " + id));
    }

    public List<Medico> listarMedicos() {
        return medicoRepositorio.buscarTodos();
    }
}
